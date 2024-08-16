package com.certification.formdatabinding.practice_project.elevatorServiceOrder;

import com.certification.formdatabinding.practice_project.MainView;
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

import static com.certification.formdatabinding.practice_project.viewComponents.CustomComponents.divider;
import static com.certification.formdatabinding.practice_project.appConfig.AppMessages.APP_MESSAGE_CORRECT_THE_FORM;
import static com.certification.formdatabinding.practice_project.appConfig.AppRoutes.ORDERS_ROUTE;
import static com.certification.formdatabinding.practice_project.elevatorServiceOrder.config.ServiceOrderLabels.*;
import static com.certification.formdatabinding.practice_project.elevatorServiceOrder.config.ServiceOrderTitles.SERVCE_ORDER_FORM_DETAILS_TITLE;
import static com.certification.formdatabinding.practice_project.elevatorServiceOrder.config.ServiceOrderTitles.SERVICE_ORDER_VIEW_TITLE;
import static com.certification.formdatabinding.practice_project.elevatorServiceOrder.config.ServiceOrderValidationMessages.*;

@Route(value = ORDERS_ROUTE, layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
public class ServiceOrderView extends VerticalLayout {

  private final Binder<ServiceOrder> binder = new Binder<>(ServiceOrder.class);
  private TextField customerNameField;
  private EmailField customerEmailField;
  private IntegerField elevatorQtdeField;
  private DatePicker deadLineField;
  private Select<String> elevatorCategoryField;

  public ServiceOrderView() {

    H1 viewTitle = new H1(SERVICE_ORDER_VIEW_TITLE);
    H2 formTitle = new H2(SERVCE_ORDER_FORM_DETAILS_TITLE);

    add(
         viewTitle,
         divider(),
         formTitle
    );

    createOrderForm();
    bind_WithValidators_formFields();
  }

  private void createOrderForm() {

    customerNameField = new TextField(CUSTOMER_NAME_LABEL);
    customerEmailField = new EmailField(CUSTOMER_EMAIL_LABEL);
    deadLineField = new DatePicker(DEADLINE_LABEL, LocalDate.now());
    elevatorQtdeField = new IntegerField(ELEVATOR_QTD_LABEL);
    elevatorQtdeField.setValue(0);

    elevatorCategoryField = new Select<>();
    elevatorCategoryField.setLabel(ELEVATOR_CATEGORY_LABEL);
    elevatorCategoryField.setItems("Lux", "Service", "Panoramic");

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
        ServiceOrder myServiceOrder = new ServiceOrder();

        // writeBean: write 'fields-values' in an Object(myOrder)
        binder.writeBean(myServiceOrder);
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

  private void bind_WithValidators_formFields() {

    binder
         // BINDER - Style 01: Bind para "propertName"(attributo)
         // Permitido pelo 'new Binder<>(Order.class)'
         // Permitido na AUSENCIA de "Validators"
         // Usado qdo o attributo tem BeanValidation
         .bind(customerNameField, "customerName");

    binder
         .forField(customerEmailField)
         .withValidator(new EmailValidator(EMAIL_VALIDATION_MESSSAGE))

         // BINDER - Style 02: Permitido pelo 'new Binder<>(Order.class)'
         .bind("customerEmail");

    binder
         .forField(elevatorQtdeField)
         .withValidator(quantity -> quantity > 0, QTDE_BIGGER_THAN_ZERO_VALIDATION_MESSSAGE)

         // BINDER - Style 03: Permitido pelo 'new Binder<>(Order.class)' or 'new Binder<>()'
         .bind(ServiceOrder::getElevatorsQuantity, ServiceOrder::setElevatorsQuantity);

    binder.bind(
         elevatorCategoryField,
         serviceOrder -> serviceOrder.getElevatorCategory(),
         (serviceOrder, categoryFieldContent) -> serviceOrder.setElevatorCategory(categoryFieldContent)
    );

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