package com.certification.formdatabinding.practice_project.elevatorCategory;

import com.certification.formdatabinding.practice_project.MainView;
import com.certification.formdatabinding.practice_project.elevatorCategory.config.ElevatorCapacityCustomConverter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.PropertyId;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

import static com.certification.formdatabinding.practice_project.appConfig.AppMessages.APP_MESSAGE_COMPLETE_FIELD_FORM;
import static com.certification.formdatabinding.practice_project.appConfig.AppRoutes.ELEVATOR_CATEGORY_ROUTE;
import static com.certification.formdatabinding.practice_project.elevatorCategory.config.ElevatorCategoryLabels.*;
import static com.certification.formdatabinding.practice_project.elevatorCategory.config.ElevatorCategoryMessages.ELEVATOR_SPEED_FORMAT_NUMBER_MESSAGE;
import static com.certification.formdatabinding.practice_project.elevatorCategory.config.ElevatorCategoryMessages.ELEVATOR_SPEED_VALIDATION_MESSAGE;
import static com.certification.formdatabinding.practice_project.elevatorCategory.config.ElevatorCategoryTitles.*;
import static com.certification.formdatabinding.practice_project.utils.MockedDataSourceElevatorCategory.randomMockedElevatorCategory;
import static com.certification.formdatabinding.practice_project.viewComponents.CustomComponents.createDivider;


@Route(value = ELEVATOR_CATEGORY_ROUTE, layout = MainView.class)
public class ElevatorCategoryView extends VerticalLayout {

  @PropertyId("categoryName")
  private final TextField categoryField = new TextField();

  @PropertyId("manufacturer")
  private final TextField brandField = new TextField();

  @PropertyId("maxCapacity")
  private final TextField capacityField = new TextField();

  @PropertyId("speed")
  private final TextField speedField = new TextField();

  @PropertyId("maxWeight")
  private final TextField weightField = new TextField();

  private final Binder<ElevatorCategory> binder = new Binder<>(ElevatorCategory.class);
  private final Grid<ElevatorCategory> gridCategoryView = new Grid<>(ElevatorCategory.class);
  private final List<ElevatorCategory> categories = new ArrayList<>();
  private ElevatorCategory categoryBean = new ElevatorCategory();

  public ElevatorCategoryView() {

    settingBinder();

    H2 titleView = new H2(ELEVATORS_CATEGORY_VIEW_TITLE);

    H2 gridTitle = new H2(ELEVATOR_CATEGORY_VIEW_GRID_TITLE);
    gridCategoryView
         .setColumns(
              "categoryName",
              "manufacturer",
              "maxCapacity",
              "speed",
              "maxWeight"
         );

    add(
         titleView,
         createDivider(),
         createForm(),
         createDivider(),
         gridTitle,
         gridCategoryView
    );

  }

  private void settingBinder() {

    binder.bindInstanceFields(this);

    binder
         .forField(categoryField)
         .withValidator(field -> field.length() > 2,
                        APP_MESSAGE_COMPLETE_FIELD_FORM
         )
         .bind(ElevatorCategory::getCategoryName, ElevatorCategory::setCategoryName);


    // todo:  CONVERTER - Style 1.0: Converter / Validator Seriados
    // - Converter + Validator segue a 'sequencia' de validacao/conversao disposta
    binder
         .forField(speedField)
         .withValidator(
              field ->
                   Double.parseDouble(field) <= 2.0, ELEVATOR_SPEED_VALIDATION_MESSAGE)
         .withConverter(new StringToDoubleConverter(ELEVATOR_SPEED_FORMAT_NUMBER_MESSAGE))
         .bind("speed"); //Bean Attribute


    // todo: CONVERTER - Style 2.0: Custom Converter
    binder
         .forField(capacityField)
         .withConverter(new ElevatorCapacityCustomConverter())
         .bind("maxCapacity"); //Bean Attribute
  }

  private VerticalLayout createForm() {

    var form = new FormLayout();

    H2 title = new H2(ELEVATOR_CATEGORY_FORM_TITLE);

    categoryField.setPlaceholder(CATEGORY_TYPE_FIELD_PLACEHOLDER);
    speedField.setPlaceholder(SPEED_FIELD_PLACEHOLDER);
    brandField.setPlaceholder(BRAND_FIELD_PLACEHOLDER);
    capacityField.setPlaceholder(CAPACITY_FIELD_PLACEHOLDER);
    weightField.setPlaceholder(MAXWEIGHT_FIELD_PLACEHOLDER);

    form.add(
         categoryField,
         speedField,
         brandField,
         capacityField,
         weightField
    );

    form
         .getChildren()
         .forEach(
              field -> field.getElement()
                            .getStyle()
                            .set("margin-bottom", "20px")
         );

    var column = new VerticalLayout();
    var row = new HorizontalLayout(createAddButton(), loadTemplateButton());
    column
         .add(
              title,
              form,
              row
         );

    return column;
  }

  private Button loadTemplateButton() {

    Button button = new Button(SUGGEST_ELEVATORS_BUTTON_LABEL);

    button
         .addClickListener(event -> {

           categoryBean = randomMockedElevatorCategory();

           // todo: BEAN - Style 2.0: setBean (Mudancas no Field, refletem no Bean RealTime - Unbuffered Changes)
           // - Set the Bean as "DataSource"
           // - Fields are Updated by the Bean
           // - Beans is updated by the Fields
           binder.setBean(categoryBean); // Clean All Fields
         });

    return button;
  }

  private Button createAddButton() {

    Button button = new Button(ADD_ELEVATORS_BUTTON_LABEL);

    button
         .addClickListener(event -> {

           Notification.show("Before Updating - The Bean is: " + categoryBean.toString());

//          todo: BEAN - Style 01: writeBeanIfValid
//             - Beans is updated by the Fields IF 'ALL Fields pass in Validation'
//             - IF Fail in Validation: don't update + return false
//
//          FieldLevel 1) Field Validators: IF Pass, go to the 'STAGE 02'(BeanLevel)
//          BeanLevel  2) Bean Attributes: IF no errors happens, write in the Bean ->
//                        IF fails - Everything is REVERTED to the STATE 'before' STAGE 01(FieldLevel)

           if (binder.writeBeanIfValid(categoryBean)) {
             categories.add(categoryBean);
             gridCategoryView.setItems(categories);

             Notification.show("After update - The Bean updating: " + categoryBean.toString());

             // todo: BEAN - Style 2.0: setBean (Mudancas no Field, refletem no Bean RealTime - Unbuffered Changes)
             // - Set the Bean as "DataSource"
             // - Fields are Updated by the Bean
             // - Beans is updated by the Fields
             binder.setBean(new ElevatorCategory()); // Clean All Fields
           }
         });

    return button;
  }
}