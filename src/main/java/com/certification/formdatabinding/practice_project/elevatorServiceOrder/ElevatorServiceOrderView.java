package com.certification.formdatabinding.practice_project.elevatorServiceOrder;

import com.certification.formdatabinding.practice_project.MainView;
import com.certification.formdatabinding.practice_project.elevatorCategory.config.ElevatorCategoryTypes;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.time.LocalDate;
import java.util.Arrays;

import static com.certification.formdatabinding.practice_project.appConfig.AppMessages.APP_MESSAGE_CORRECT_THE_FORM;
import static com.certification.formdatabinding.practice_project.appConfig.AppRoutes.ORDERS_ROUTE;
import static com.certification.formdatabinding.practice_project.elevatorServiceOrder.config.ElevatorServiceOrderLabels.*;
import static com.certification.formdatabinding.practice_project.elevatorServiceOrder.config.ElevatorServiceOrderTitles.SERVCE_ORDER_FORM_DETAILS_TITLE;
import static com.certification.formdatabinding.practice_project.elevatorServiceOrder.config.ElevatorServiceOrderTitles.SERVICE_ORDER_VIEW_TITLE;
import static com.certification.formdatabinding.practice_project.elevatorServiceOrder.config.ElevatorServiceOrderValidationMessages.*;
import static com.certification.formdatabinding.practice_project.viewComponents.CustomComponents.createDivider;

@Route(value = ORDERS_ROUTE, layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
public class ElevatorServiceOrderView extends VerticalLayout {

  private final Binder<ElevatorServiceOrder> binder = new Binder<>(ElevatorServiceOrder.class);
  private TextField customerNameField;
  private EmailField customerEmailField;
  private IntegerField elevatorQtdeField;
  private DatePicker deadLineField;
  private Select<String> elevatorCategoryField;

  public ElevatorServiceOrderView() {

    H1 viewTitle = new H1(SERVICE_ORDER_VIEW_TITLE);
    H2 formTitle = new H2(SERVCE_ORDER_FORM_DETAILS_TITLE);

    add(
         viewTitle,
         createDivider(),
         formTitle
    );

    createForm();
    settingBinder();
  }

  private void createForm() {

    customerNameField = new TextField(CUSTOMER_NAME_LABEL);
    customerEmailField = new EmailField(CUSTOMER_EMAIL_LABEL);
    deadLineField = new DatePicker(DEADLINE_LABEL, LocalDate.now());
    elevatorQtdeField = new IntegerField(ELEVATOR_QTD_LABEL);
    elevatorQtdeField.setValue(0);

    elevatorCategoryField = new Select<>();
    elevatorCategoryField.setLabel(ELEVATOR_CATEGORY_LABEL);
    elevatorCategoryField.setItems(
         Arrays
              .stream(ElevatorCategoryTypes.values())
              .map(ElevatorCategoryTypes::getName)
              .toList()
    );

    var formLayout = new FormLayout();

    formLayout.add(
         customerNameField,
         customerEmailField,
         elevatorQtdeField,
         elevatorCategoryField,
         deadLineField
    );

    formLayout
         .setResponsiveSteps(
              new FormLayout.ResponsiveStep("0", 1),
              new FormLayout.ResponsiveStep("500px", 2)
         );

    Button submitOrder = new Button(ADD_SERVICE_ORDER_LABEL, event -> {
      try {

        var myElevatorServiceOrder = new ElevatorServiceOrder();

        // todo:  writeBean: write 'fields-values' in an Object(myOrder)
        binder.writeBean(myElevatorServiceOrder);
        processOrder();
      }
      catch (ValidationException e) {
        Notification.show(
             APP_MESSAGE_CORRECT_THE_FORM,
             3000,
             Notification.Position.MIDDLE
        );
      }
    });

    add(formLayout, submitOrder);
  }

  private void settingBinder() {

    binder
         // todo:  BINDER - Style 01: Bind para "propertName"(attributo)
         // Permitido pelo 'new Binder<>(Order.class)'
         // Permitido na AUSENCIA de "Validators"
         // Usado qdo o attributo tem BeanValidation
         .bind(customerNameField, "customerName");

    // todo:  VALIDATOR - Style 3.0: Vaadin Standard Validator
    // - Validator Standard do Vaadin
    binder
         .forField(customerEmailField)
         .withValidator(new EmailValidator(EMAIL_VALIDATION_MESSSAGE))

         // todo:  BINDER - Style 02: Permitido pelo 'new Binder<>(Order.class)'
         .bind("customerEmail");


    // todo:  VALIDATOR - Style 1.0: Lambda Validator for Numbers
    // - Validator customizado com Lambda p/ numbers
    binder
         .forField(elevatorQtdeField)
         .withValidator(quantity -> quantity > 0, QTDE_BIGGER_THAN_ZERO_VALIDATION_MESSSAGE)

         // todo:  BINDER - Style 03: Permitido pelo 'new Binder<>(Order.class)' or 'new Binder<>()'
         .bind(ElevatorServiceOrder::getElevatorsQuantity,
               ElevatorServiceOrder::setElevatorsQuantity
         );

    binder.bind(
         elevatorCategoryField,
         elevatorServiceOrder -> elevatorServiceOrder.getElevatorCategory(),
         (elevatorServiceOrder, categoryFieldContent) ->
              elevatorServiceOrder.setElevatorCategory(categoryFieldContent)
    );

    // todo:  VALIDATOR - Style 2.0: Lambda Validator for Date
    // - Validator customizado com Lambda p/ data
    binder
         .forField(deadLineField)
         .asRequired(DEADLINE_REQUIRED_MESSSAGE)
         .withValidator(
              date -> date.isAfter(LocalDate.now()),
              DEADLINE_IN_THE_FUTURE_VALIDATION_MESSSAGE
         )
         .bind("deadline");
  }

  private void processOrder() {

    // Simulating order processing
    final String text =
         "Order processed %s - Quantity %s!"
              .formatted(
                   customerNameField.getValue(),
                   elevatorQtdeField.getValue()
                                    .toString()
              );

    Notification.show(text, 3000, Notification.Position.MIDDLE);
  }
}