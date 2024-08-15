package com.certification.formdatabinding.practice_project.elevatorCategory;

import com.certification.formdatabinding.practice_project.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.PropertyId;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

import static com.certification.formdatabinding.practice_project.components.CustomComponents.divider;
import static com.certification.formdatabinding.practice_project.config.AppRoutes.ELEVATOR_CATEGORY_ROUTE;
import static com.certification.formdatabinding.practice_project.elevatorCategory.config.ElevatorCategoryLabels.*;
import static com.certification.formdatabinding.practice_project.elevatorCategory.config.ElevatorCategoryTitles.*;


@Route(value = ELEVATOR_CATEGORY_ROUTE, layout = MainView.class)
public class ElevatorCategoryView extends VerticalLayout {

  @PropertyId("categoryName")
  private final TextField category = new TextField();

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

  public ElevatorCategoryView() {

    binder.bindInstanceFields(this);

    H2 titleView = new H2(ELEVATORS_CATEGORY_VIEW_TITLE);

    H2 gridTitle = new H2(ELEVATOR_CATEGORY_GRID_TITLE);
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
         divider(),
         createForm(),
         divider(),
         gridTitle,
         gridCategoryView
    );

  }

  private VerticalLayout createForm() {

    var newCategory = new ElevatorCategory();

    var form = new FormLayout();

    H2 title = new H2(ELEVATOR_CATEGORY_FORM_TITLE);

    category.setPlaceholder(PLACEHOLCER_FIELDS_CATEGORY);
    speed.setPlaceholder(PLACEHOLCER_FIELDS_SPEED);
    brand.setPlaceholder(PLACEHOLCER_FIELDS_BRAND);
    weight.setPlaceholder(PLACEHOLCER_FIELDS_MAXWEIGHT);
    capacity.setPlaceholder(PLACEHOLCER_FIELDS_CAPACITY);

    form.add(
         category,
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
                            .set("margin-bottom", "20px"));

    var column = new VerticalLayout();

    column
         .add(
              title,
              form,
              createAddCategoryButton(newCategory)
         );

    return column;
  }

  private Button createAddCategoryButton(ElevatorCategory category) {

    Button button = new Button(ADD_ELEVATORS_CATEGORY);

    button
         .addClickListener(event -> {

           // BEAN - Style 01: writeBeanIfValid
           // - check Validation of AllFields +
           // - 'write' "Field-Content" para "Entity-Attribute"
           if (binder.writeBeanIfValid(category)) {
             categories.add(category);
             gridCategoryView.setItems(categories);
           }
         });

    return button;
  }
}