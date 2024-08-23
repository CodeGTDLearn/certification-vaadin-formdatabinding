package com.certification.formdatabinding.practice_project.elevatorClient.views;

import com.certification.formdatabinding.practice_project.MainView;
import com.certification.formdatabinding.practice_project.elevatorClient.entity.ElevatorClientAddress;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.PropertyId;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

import static com.certification.formdatabinding.practice_project.utils.MockedDataSourceElevatorCategory.mockedElevatorClientAddress;
import static com.certification.formdatabinding.practice_project.viewComponents.CustomComponents.createDivider;
import static com.certification.formdatabinding.practice_project.appConfig.AppMessages.APP_MESSAGE_COMPLETE_THE_FORM;
import static com.certification.formdatabinding.practice_project.appConfig.AppRoutes.CLIENTS_ADDRESS_ROUTE;
import static com.certification.formdatabinding.practice_project.elevatorClient.config.ElevatorClientAddressLabels.*;
import static com.certification.formdatabinding.practice_project.elevatorClient.config.ElevatorClientAddressTitles.*;

@Route(value = CLIENTS_ADDRESS_ROUTE, layout = MainView.class)
public class ElevatorClientAddressView extends VerticalLayout {

  // BINDER: Automatic Binding according Entity-Attribute name
  private TextField streetAddress = new TextField();

  @PropertyId("city")
  private TextField cityField = new TextField();

  //  Automatic Binding according Entity-Attribute name
  private TextField postalCode = new TextField();

  private Binder<ElevatorClientAddress> binder = new Binder<>(ElevatorClientAddress.class);
  private  Grid<ElevatorClientAddress> grid = new Grid<>(ElevatorClientAddress.class);

  private ElevatorClientAddress elevatorClientAddress = new ElevatorClientAddress();
  private List<ElevatorClientAddress> listAdress = new ArrayList<>();

  public ElevatorClientAddressView() {

    H1 viewTitle = new H1(CLIENT_ADDRESS_VIEW_TITLE);

    binder.bindInstanceFields(this);

    add(
         viewTitle,
         createDivider(),
         createForm(),
         createDivider(),
         createGrid()
    );
  }

  private Grid<ElevatorClientAddress> createGrid() {

    add(new H2(CLIENT_ADDRESS_VIEW_GRID_TITLE));

    grid.setColumns("streetAddress", "city", "postalCode");

    return grid;
  }

  private VerticalLayout createForm() {

    H2 formTitle = new H2(CLIENT_ADDRESS_VIEW_FORM_TITLE);

    var form = new FormLayout();
    cityField.setLabel(CITY_LABEL);
    streetAddress.setLabel(STREET_LABEL);
    postalCode.setLabel(POSTALCODE_LABEL);

    form.add(streetAddress, postalCode, cityField);

    var row = new HorizontalLayout();
    row.add(createAddButton(), loadTemplateButton());

    var column = new VerticalLayout();
    column.add(form, row);

    return column;
  }

  private Button createAddButton() {

    var saveButton = new Button(ADD_CLIENT_BUTTON_LABEL);

    saveButton
         .addClickListener(clickEvent -> {


           // BEAN: writeBean (fields -> bean) | Buffered Writing
           try {
             binder.writeBean(elevatorClientAddress);
           }
           catch (ValidationException e) {
             throw new RuntimeException(e);
           }

           if (isAddressFilled(elevatorClientAddress)) {

             listAdress.add(elevatorClientAddress);
             grid.setItems(listAdress);

             var sucessSaving =
                  "Saved Data: %s %n %s %n %s."
                       .formatted(elevatorClientAddress.getStreetAddress(), elevatorClientAddress.getCity(),
                                  elevatorClientAddress.getPostalCode()
                       );

             Notification.show(sucessSaving);

           } else Notification.show(APP_MESSAGE_COMPLETE_THE_FORM);
         });

    return saveButton;
  }

  private Button loadTemplateButton() {

    var button = new Button(TEMPLATE_CLIENT_BUTTON_LABEL);

    button
         .addClickListener(event -> {

           var elevatorClientAddressTemplateBean = mockedElevatorClientAddress();

           // BEAN: readBean (load bean in the Fields | Buffered Readind)
           binder.readBean(elevatorClientAddressTemplateBean);
         });

    return button;
  }

  private boolean isAddressFilled(ElevatorClientAddress adress) {

    return
         !adress.getStreetAddress().isBlank() &&
         !adress.getCity().isBlank() &&
         !adress.getPostalCode().isBlank();

  }
}