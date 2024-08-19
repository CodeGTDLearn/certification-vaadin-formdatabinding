package com.certification.formdatabinding.practice_project.elevatorCategory;

import com.certification.formdatabinding.practice_project.MainView;
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
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

import static com.certification.formdatabinding.practice_project.appConfig.AppMessages.APP_MESSAGE_COMPLETE_FIELD_FORM;
import static com.certification.formdatabinding.practice_project.appConfig.AppRoutes.ELEVATOR_CATEGORY_ROUTE;
import static com.certification.formdatabinding.practice_project.elevatorCategory.config.ElevatorCategoryLabels.*;
import static com.certification.formdatabinding.practice_project.elevatorCategory.config.ElevatorCategoryTitles.*;
import static com.certification.formdatabinding.practice_project.utils.MockedDataSourceElevatorCategory.createRandomMockedElevatorCategory;
import static com.certification.formdatabinding.practice_project.viewComponents.CustomComponents.createDivider;


@Route(value = ELEVATOR_CATEGORY_ROUTE, layout = MainView.class)
public class ElevatorCategoryView extends VerticalLayout {

  @PropertyId("categoryName")
  private final TextField categoryType = new TextField();

  @PropertyId("manufacturer")
  private final TextField brand = new TextField();

  @PropertyId("maxCapacity")
  private final TextField capacity = new TextField();

  @PropertyId("speed")
  private final TextField speed = new TextField();

  @PropertyId("maxWeight")
  private final TextField weight = new TextField();

  private final Binder<ElevatorCategory> binder = new Binder<>(ElevatorCategory.class);
  private final Grid<ElevatorCategory> gridCategoryView = new Grid<>(ElevatorCategory.class);
  private final List<ElevatorCategory> categories = new ArrayList<>();
  private ElevatorCategory categoryBean = new ElevatorCategory();

  public ElevatorCategoryView() {

    createTheViewBinders();

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

  private void createTheViewBinders() {

    binder.bindInstanceFields(this);

    binder
         .forField(categoryType)
         .withValidator(categoryField -> categoryField.length() > 2,
                        APP_MESSAGE_COMPLETE_FIELD_FORM
         )
         .bind(
              ElevatorCategory::getCategoryName, ElevatorCategory::setCategoryName
         );
  }

  private VerticalLayout createForm() {

    var form = new FormLayout();

    H2 title = new H2(ELEVATOR_CATEGORY_FORM_TITLE);

    categoryType.setPlaceholder(CATEGORY_TYPE_FIELD_PLACEHOLDER);
    speed.setPlaceholder(SPEED_FIELD_PLACEHOLDER);
    brand.setPlaceholder(BRAND_FIELD_PLACEHOLDER);
    capacity.setPlaceholder(CAPACITY_FIELD_PLACEHOLDER);
    weight.setPlaceholder(MAXWEIGHT_FIELD_PLACEHOLDER);

    form.add(
         categoryType,
         speed,
         brand,
         capacity,
         weight
    );

    form
         .getChildren()
         .forEach(
              field -> field.getElement()
                            .getStyle()
                            .set("margin-bottom", "20px")
         );

    var column = new VerticalLayout();
    var row = new HorizontalLayout(createAddCategoryButton(), createSuggestCategoryButton());
    column
         .add(
              title,
              form,
              row
         );

    return column;
  }

  private Button createSuggestCategoryButton() {

    Button button = new Button(SUGGEST_ELEVATORS_BUTTON_LABEL);

    button
         .addClickListener(event -> {

           categoryBean = createRandomMockedElevatorCategory();

           // BEAN - Style 2.0: setBean (Mudancas no Field, refletem no Bean RealTime)
           // - Set the Bean as "DataSource"
           // - Fields are Updated by the Bean
           // - Beans is updated by the Fields
           binder.setBean(categoryBean); // Clean All Fields
         });

    return button;
  }

  private Button createAddCategoryButton() {

    Button button = new Button(ADD_ELEVATORS_BUTTON_LABEL);

    button
         .addClickListener(event -> {

           Notification.show("Before Updating - The Bean is: " + categoryBean.toString());
/*
              BEAN - Style 01: writeBeanIfValid
                - Beans is updated by the Fields IF pass "Validating"
                  - "Validate Failing"(FieldLevel -> BeanLevel):
                - don't update + return false

             FieldLevel 1) Field Validators: IF Pass, go to the 'STAGE 02'
             BeanLevel  2) Bean Validators: Check BeanValidation ->
                           IF fails - Everything is REVERTED to the STATE 'before' STAGE 01
*/
           if (binder.writeBeanIfValid(categoryBean)) {
             categories.add(categoryBean);
             gridCategoryView.setItems(categories);

             Notification.show("After update - The Bean updateding: " + categoryBean.toString());

             // BEAN - Style 2.0: setBean (Mudancas no Field, refletem no Bean RealTime)
             // - Set the Bean as "DataSource"
             // - Fields are Updated by the Bean
             // - Beans is updated by the Fields
             binder.setBean(new ElevatorCategory()); // Clean All Fields
           }
         });

    return button;
  }
}