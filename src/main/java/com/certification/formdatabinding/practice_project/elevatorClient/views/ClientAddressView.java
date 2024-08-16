package com.certification.formdatabinding.practice_project.elevatorClient.views;

import com.certification.formdatabinding.practice_project.MainView;
import com.certification.formdatabinding.practice_project.elevatorClient.entity.ClientAddress;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.PropertyId;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

import static com.certification.formdatabinding.practice_project.viewComponents.CustomComponents.divider;
import static com.certification.formdatabinding.practice_project.appConfig.AppMessages.APP_MESSAGE_COMPLETE_THE_FORM;
import static com.certification.formdatabinding.practice_project.appConfig.AppRoutes.CLIENTS_ADDRESS_ROUTE;
import static com.certification.formdatabinding.practice_project.elevatorClient.config.ClientAddressViewLabels.*;
import static com.certification.formdatabinding.practice_project.elevatorClient.config.ClientAddressViewTitles.*;

@Route(value = CLIENTS_ADDRESS_ROUTE, layout = MainView.class)
public class ClientAddressView extends VerticalLayout {

  // BINDER - Style 09: Automatic Binding according Entity-Attribute name
  private TextField streetAddress = new TextField();

  @PropertyId("city")
  private TextField cityField = new TextField();

  //  Automatic Binding according Entity-Attribute name
  private TextField postalCode = new TextField();

  private Binder<ClientAddress> binder = new Binder<>(ClientAddress.class);
  private  Grid<ClientAddress> grid = new Grid<>(ClientAddress.class);

  private ClientAddress clientAddress = new ClientAddress();
  private List<ClientAddress> listAdress = new ArrayList<>();

  public ClientAddressView() {

    H1 viewTitle = new H1(CLIENT_ADDRESS_VIEW_TITLE);
    H2 formTitle = new H2(CLIENT_ADDRESS_VIEW_FORM_TITLE);

    binder.bindInstanceFields(this);

    H2 gridTitle = new H2(CLIENT_ADDRESS_VIEW_GRID_TITLE);
    grid.setColumns("streetAddress", "city", "postalCode");

    add(
         viewTitle,
         divider(),
         formTitle,
         createCustomerAddressForm(),
         divider(),
         gridTitle,
         grid
    );
  }

  private VerticalLayout createCustomerAddressForm() {

    var form = new FormLayout();
    cityField.setLabel(CLIENT_ADDRESS_CITY_LABEL);
    streetAddress.setLabel(CLIENT_ADDRESS_STREET_LABEL);
    postalCode.setLabel(CLIENT_ADDRESS_POSTALCODE_LABEL);

    form.add(streetAddress, postalCode, cityField);

    var saveButton = new Button(CLIENT_ADDRESS_ADD_CLIENT_BUTTON_LABEL);

    saveButton
         .addClickListener(clickEvent -> {

           try {
             binder.writeBean(clientAddress);
           }
           catch (ValidationException e) {
             throw new RuntimeException(e);
           }

           if (isAddressFilled(clientAddress)) {

             listAdress.add(clientAddress);
             grid.setItems(listAdress);

             var sucessSaving =
                  "Saved Data: %s %n %s %n %s."
                       .formatted(clientAddress.getStreetAddress(), clientAddress.getCity(),
                                  clientAddress.getPostalCode()
                       );

             Notification.show(sucessSaving);

           } else Notification.show(APP_MESSAGE_COMPLETE_THE_FORM);
         });

    var column = new VerticalLayout();
    column.add(form, saveButton);

    return column;
  }

  private boolean isAddressFilled(ClientAddress adress) {

    return
         !adress.getStreetAddress().isBlank() &&
         !adress.getCity().isBlank() &&
         !adress.getPostalCode().isBlank();

  }
}