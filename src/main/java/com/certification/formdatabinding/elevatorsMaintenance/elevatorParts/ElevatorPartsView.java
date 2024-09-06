package com.certification.formdatabinding.elevatorsMaintenance.elevatorParts;

import com.certification.formdatabinding.elevatorsMaintenance.MainView;
import com.certification.formdatabinding.elevatorsMaintenance.utils.customViewComponents.CustomDataPicker;
import com.certification.formdatabinding.elevatorsMaintenance.utils.customViewComponents.my_sec_comp.CustomMaterialSwitch;
import com.certification.formdatabinding.elevatorsMaintenance.utils.customViewComponents.paper_toggle_button.CustomToggleButton;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ReadOnlyHasValue;
import com.vaadin.flow.router.Route;

import java.time.LocalDateTime;

import static com.certification.formdatabinding.elevatorsMaintenance.config.AppRoutes.ELEVATOR_PARTS_ROUTE;
import static com.certification.formdatabinding.elevatorsMaintenance.utils.customViewComponents.UtilComponents.createDivider;
import static com.certification.formdatabinding.elevatorsMaintenance.utils.customViewComponents.UtilComponents.showNotification;
import static com.certification.formdatabinding.elevatorsMaintenance.elevatorParts.config.ElevatorPartLabels.*;
import static com.certification.formdatabinding.elevatorsMaintenance.elevatorParts.config.ElevatorPartTitles.ELEVATORS_PARTS_FORM_TITLE;
import static com.certification.formdatabinding.elevatorsMaintenance.elevatorParts.config.ElevatorPartTitles.ELEVATORS_PARTS_VIEW_TITLE;
import static com.certification.formdatabinding.elevatorsMaintenance.elevatorParts.config.ElevatorPartsMessages.MIN_TEXT_DESCRIPTION_MESSSAGE;
import static com.certification.formdatabinding.elevatorsMaintenance.elevatorParts.config.ElevatorPartsMessages.TEXT_MANUFACTER_MESSSAGE;
import static com.certification.formdatabinding.elevatorsMaintenance.utils.MockedDataSourceElevatorCategory.randomMockedElevatorPart;
import static com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import static java.lang.Character.isUpperCase;

@Route(value = ELEVATOR_PARTS_ROUTE, layout = MainView.class)
public class ElevatorPartsView extends VerticalLayout {

  BeanValidationBinder<ElevatorParts> binder =
       new BeanValidationBinder<>(ElevatorParts.class);

  private TextField partNameField = new TextField();
  private TextField manufacturerField = new TextField();
  private TextArea descriptionField = new TextArea();
  private IntegerField quantityField = new IntegerField();
  private CustomDataPicker dataPicker = new CustomDataPicker();
  private Button mostCommonPartsButton = new Button();
  private H2 displayTextMostCommonPart = new H2();
  private Button resumeLaterButton = new Button();

  private ElevatorParts elevatorPart = new ElevatorParts();

  public ElevatorPartsView() {

    displayTextMostCommonPart.getStyle()
                             .setColor("Blue");

    settingBinder();

    H1 titleView = new H1(ELEVATORS_PARTS_VIEW_TITLE);
    H2 formTitle = new H2(ELEVATORS_PARTS_FORM_TITLE);

    var subLine1 = new HorizontalLayout();
    subLine1.add(formTitle, displayTextMostCommonPart);
    subLine1.setWidthFull();
    subLine1.setWidth("50%");

    var subLine2 = createMaterialSwitch(
         "25%",
         "Enable 'Resume Later'"
    );

    var subLine3 = createToggleButton(
         "25%",
         "Enable 'Most Common Parts' Button"
    );

    var row = new HorizontalLayout();
    row.setWidthFull();
    row.add(subLine1, subLine2, subLine3);

    mostCommonPartsButton = createMostCommonPartsButton();

    var rowButtons = new HorizontalLayout(
         createSaveDraftButton(),
         createResumeLaterButton(),
         mostCommonPartsButton
    );

    rowButtons.setAlignItems(Alignment.CENTER);
    rowButtons.setWidthFull();

    add(
         titleView,
         createDivider(),
         row,
         createForm(),
         rowButtons
    );
  }


  private HorizontalLayout createToggleButton(
       String sizePercentual,
       String label) {

    var row = new HorizontalLayout();
    row.setVerticalComponentAlignment(Alignment.CENTER);
    row.setWidth(sizePercentual);

    var toggleButton = new CustomToggleButton();
    toggleButton.setValue(true);
    var toggleButtonLabel = new Text(label);
    toggleButton
         .addValueChangeListener(
              event -> {
                var newValue = event.getValue();

                mostCommonPartsButton.setEnabled(newValue);
              });

    row.add(toggleButtonLabel, toggleButton);

    return row;
  }

  private HorizontalLayout createMaterialSwitch(
       String sizePercentual,
       String label) {

    var row = new HorizontalLayout();
    row.setVerticalComponentAlignment(Alignment.CENTER);
    row.setWidth(sizePercentual);

    var aSwitch = new CustomMaterialSwitch();
    
    aSwitch.setValue(true);

    var aSwitchLabel = new Text(label);

    aSwitch
         .addValueChangeListener(
              event -> {
                var newValue = event.getValue();

                resumeLaterButton.setEnabled(newValue);
              });

    row.add(aSwitchLabel, aSwitch);

    return row;
  }

  private FormLayout createForm() {

    partNameField.setPlaceholder(PARTNAME_PLACEHOLDER);
    manufacturerField.setPlaceholder(MANUFACTURER_PLACEHOLDER);
    quantityField.setPlaceholder(QUANTITY_PLACEHOLDER);
    dataPicker.setValue(LocalDateTime.now());

    descriptionField.setPlaceholder(DESCRIPTION_PLACEHOLDER);
    descriptionField.setHeight("10em");

    var formLayout = new FormLayout();

    formLayout
         .add(
              partNameField,
              manufacturerField,
              quantityField,
              dataPicker,
              descriptionField
         );

    formLayout
         .setResponsiveSteps(

              // Use one column by default
              new ResponsiveStep("0", 1),

              // Use 3 columns, if layout's width exceeds 500px
              new ResponsiveStep("500px", 4)
         );

    // Stretch the username field over the 3 columns
    formLayout.setColspan(descriptionField, 4);

    return formLayout;
  }

  private void settingBinder() {

    binder.bind(partNameField, "partName");
    binder.bind(manufacturerField, "manufacturer");
    binder.bind(descriptionField, "description");

    // BINDER: BeanValidationBinder (check Bean Validation)
    binder.forField(quantityField)
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
                        isUpperCase(text.charAt(0)),
              TEXT_MANUFACTER_MESSSAGE
         )
         .bind("manufacturer");
  }

  private Button createMostCommonPartsButton() {

    var button = new Button(ADD_PART_BUTTON_LABEL);

    // READ-ONLY-HAS-VALUE: Exiba(ReadOnly) Entity-Data to UI-Items
    var H2_UI_changer = new ReadOnlyHasValue<String>(
         text -> displayTextMostCommonPart.setText(text)
    );

    binder
         .forField(H2_UI_changer)
         .bind(ElevatorParts::getPartName, null);

    button.addClickListener(event -> {
      var elevatorPart = randomMockedElevatorPart();
      binder.readBean(elevatorPart);
    });

    return button;
  }

  private Button createSaveDraftButton() {

    var button = new Button(SAVE_DRAFT_BUTTON_LABEL);

    showBean("Antes (Simples): ");

    // BEAN: writeBeanAsDraft (Simple)
    // - Salva os campos validos + invalidos(colocal null)
    button.addClickListener(event -> {
      binder.writeBeanAsDraft(elevatorPart);
      showBean("Depois (Simples): ");
    });

    return button;
  }

  private Button createResumeLaterButton() {

    resumeLaterButton = new Button(RESUME_LATER_BUTTON_LABEL);

    showBean("Antes (Forced): ");

    // BEAN: writeBeanAsDraft (Forced)
    // - Salva os campos validos + invalidos(salva mesmo/incluindo a invalidez)
    resumeLaterButton.addClickListener(event -> {
      binder.writeBeanAsDraft(elevatorPart, true);
      showBean("Apos (Forced): ");
    });

    return resumeLaterButton;
  }

  private void showBean(String moment) {

    showNotification(
         "%s: %s"
              .formatted(
                   moment,
                   elevatorPart.toString()
              ),
         Notification.Position.MIDDLE
    );
  }
}