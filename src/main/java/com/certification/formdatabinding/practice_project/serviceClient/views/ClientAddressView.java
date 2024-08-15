package com.certification.formdatabinding.practice_project.serviceClient.views;

import com.certification.formdatabinding.practice_project.MainView;
import com.certification.formdatabinding.practice_project.serviceClient.entity.ClientAddress;
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

import static com.certification.formdatabinding.practice_project.components.CustomComponents.divider;
import static com.certification.formdatabinding.practice_project.config.AppRoutes.CLIENTS_ADDRESS_ROUTE;
import static com.certification.formdatabinding.practice_project.serviceClient.config.ServiceClientTitles.*;

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

    H1 viewTitle = new H1(CLIENTS_ADDRESS_VIEW_TITLE);
    H2 formTitle = new H2(CLIENT_ADDRESS_FORM_TITLE);

    binder.bindInstanceFields(this);

    H2 gridTitle = new H2(CLIENT_ADDRESS_GRID_TITLE);
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
    cityField.setLabel("City");
    streetAddress.setLabel("Street Address");
    postalCode.setLabel("Postal Code");

    form.add(streetAddress, postalCode, cityField);

    var saveButton = new Button("Save");

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

           } else Notification.show("Please complete the form.");
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