package com.certification.formdatabinding.practice_project.elevatorParts;

import com.certification.formdatabinding.practice_project.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ReadOnlyHasValue;
import com.vaadin.flow.router.Route;

import static com.certification.formdatabinding.practice_project.appConfig.AppRoutes.ELEVATOR_PARTS_ROUTE;
import static com.certification.formdatabinding.practice_project.elevatorParts.config.ElevatorPartLabels.*;
import static com.certification.formdatabinding.practice_project.elevatorParts.config.ElevatorPartTitles.ELEVATORS_PARTS_FORM_TITLE;
import static com.certification.formdatabinding.practice_project.elevatorParts.config.ElevatorPartTitles.ELEVATORS_PARTS_VIEW_TITLE;
import static com.certification.formdatabinding.practice_project.elevatorParts.config.ElevatorPartsMessages.MIN_TEXT_DESCRIPTION_MESSSAGE;
import static com.certification.formdatabinding.practice_project.elevatorParts.config.ElevatorPartsMessages.TEXT_MANUFACTER_MESSSAGE;
import static com.certification.formdatabinding.practice_project.utils.MockedDataSourceElevatorCategory.mockedElevatorPart;
import static com.certification.formdatabinding.practice_project.viewComponents.CustomComponents.createDivider;
import static com.certification.formdatabinding.practice_project.viewComponents.CustomComponents.showNotification;
import static com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;

@Route(value = ELEVATOR_PARTS_ROUTE, layout = MainView.class)
public class ElevatorPartsView extends VerticalLayout {

  BeanValidationBinder<ElevatorParts> binder =
       new BeanValidationBinder<>(ElevatorParts.class);

  private final TextField partNameField = new TextField();
  private final TextField manufacturerField = new TextField();
  private final TextArea descriptionField = new TextArea();
  private final IntegerField quantityField = new IntegerField();

  private H2 UI_Item_H2 = new H2();
  private H3 UI_Item_H3 = new H3();

  private ElevatorParts elevatorPart = new ElevatorParts();

  public ElevatorPartsView() {

    settingBinder();

    H1 titleView = new H1(ELEVATORS_PARTS_VIEW_TITLE);
    H2 formTitle = new H2(ELEVATORS_PARTS_FORM_TITLE);

    HorizontalLayout rowTitle = new HorizontalLayout();
    UI_Item_H2.getStyle()
              .setColor("Blue");
    UI_Item_H3.getStyle()
              .setColor("Red");

    rowTitle.add(formTitle, UI_Item_H2, UI_Item_H3);

    add(
         titleView,
         createDivider(),
         rowTitle,
         createForm(),
         createUiChangerButton()
    );

  }

  private FormLayout createForm() {

    partNameField.setPlaceholder(PARTNAME_PLACEHOLDER);
    manufacturerField.setPlaceholder(MANUFACTURER_PLACEHOLDER);
    quantityField.setPlaceholder(QUANTITY_PLACEHOLDER);

    descriptionField.setPlaceholder(DESCRIPTION_PLACEHOLDER);
    descriptionField.setHeight("10em");

    FormLayout formLayout = new FormLayout();

    formLayout
         .add(
              partNameField,
              manufacturerField,
              quantityField,
              descriptionField
         );

    formLayout
         .setResponsiveSteps(
              // Use one column by default
              new ResponsiveStep("0", 1),

              // Use 3 columns, if layout's width exceeds 500px
              new ResponsiveStep("500px", 3)
         );

    // Stretch the username field over the 3 columns
    formLayout.setColspan(descriptionField, 3);

    return formLayout;
  }

  private void settingBinder() {

    binder.bind(partNameField, "partName");
    binder.bind(manufacturerField, "manufacturer");
    binder.bind(descriptionField, "description");

    // todo: BINDER: BeanValidationBinder
    binder
         .forField(quantityField)
         .bind("quantity");

    binder
         .forField(descriptionField)
         .withValidator(
              (text) -> text.length() >= 10 &&
                        text.contains(" "),
              MIN_TEXT_DESCRIPTION_MESSSAGE
         )
         .bind("description");

    binder
         .forField(manufacturerField)
         .withValidator(
              (text) -> text.length() >= 3 &&
                        Character.isUpperCase(text.charAt(0)),
              TEXT_MANUFACTER_MESSSAGE
         )
         .bind("description");
  }

  private Button createUiChangerButton() {

    var button = new Button(ADD_PART_BUTTON_LABEL);

    elevatorPart = mockedElevatorPart();

    // todo:  ReadOnlyHasValue: Exiba(ReadOnly) Entity-Data em Elementos de UI
    ReadOnlyHasValue<String> UI_Item_H2_changer =
         new ReadOnlyHasValue<>(
              text -> UI_Item_H2.setText(text)
         );

    ReadOnlyHasValue<String> UI_Item_H3_changer =
         new ReadOnlyHasValue<>(
              text -> UI_Item_H3.setText(text)
         );

    binder
         .forField(UI_Item_H2_changer)
         .bind(ElevatorParts::getPartName, null);

    binder
         .forField(UI_Item_H3_changer)
         .bind(ElevatorParts::getManufacturer, null);

    button.addClickListener(event -> {
      binder.readBean(elevatorPart);
    });

    return button;
  }

  private Button createSaveDraftButton() {

    var button = new Button(SAVE_DRAFT_BUTTON_LABEL);

    showNotification("Antes do draft simples", Notification.Position.MIDDLE);

    button.addClickListener(event -> {
      binder.writeBeanAsDraft(elevatorPart);

      showNotification("Antes do draft simples", Notification.Position.MIDDLE);
    });

    return button;
  }

  private Button createResumeLaterButton() {

    var button = new Button(RESUME_LATER_BUTTON_LABEL);

    showNotification("Antes do draft force", Notification.Position.MIDDLE);

    button.addClickListener(event -> {
      binder.writeBeanAsDraft(elevatorPart, true);
      showNotification("Depois do draft force", Notification.Position.MIDDLE);
    });

    return button;
  }
}