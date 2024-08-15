package com.certification.formdatabinding.practice_project.serviceClient.views;

import com.certification.formdatabinding.practice_project.MainView;
import com.certification.formdatabinding.practice_project.serviceClient.entity.Client;
import com.certification.formdatabinding.practice_project.serviceClient.entity.ClientAddress;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import static com.certification.formdatabinding.practice_project.components.CustomComponents.divider;
import static com.certification.formdatabinding.practice_project.config.AppRoutes.CLIENTS_ROUTE;
import static com.certification.formdatabinding.practice_project.serviceClient.config.ServiceClientTitles.CLIENTS_VIEW_TITLE;
import static com.certification.formdatabinding.practice_project.serviceClient.config.ServiceClientTitles.CLIENT_FORM_TITLE;

@Route(value = CLIENTS_ROUTE, layout = MainView.class)
public class ClientView extends VerticalLayout {

  private final Client client = new Client();
  private final ClientAddress clientAddress = new ClientAddress();

  private Binder<Client> binder = new Binder<>(Client.class);

  private TextField firstNameField;
  private TextField lastNameField;
  private TextField emailField;

  private TextField streetAddressField;
  private TextField postalCodeField;
  private TextField cityField;

  public ClientView() {

    H1 viewTitle = new H1(CLIENTS_VIEW_TITLE);
    H2 formTitle = new H2(CLIENT_FORM_TITLE);

    client.setClientAddress(clientAddress);

    var customerForm = createCustomerForm();

    addressBinder();

    customerBinder();

    add(
         viewTitle,
         divider(),
         formTitle,
         customerForm
    );
  }

  private VerticalLayout createCustomerForm() {

    FormLayout form = new FormLayout();

    firstNameField = new TextField("First Name");
    lastNameField = new TextField("Last Name");
    emailField = new TextField("Email");

    streetAddressField = new TextField("Street Address");
    postalCodeField = new TextField("Postal Code");
    cityField = new TextField("City");

    Button saveButton = new Button("Save");

    saveButton
         .addClickListener(event -> {
           if (binder.writeBeanIfValid(client)) {
             Notification.show(
                  "Data Saved: %s %n %s %n %s %n %s %n %s"
                       .formatted(
                            client.getFirstName(),
                            client.getLastName(),
                            client.getEmail(),
                            client.getClientAddress().getStreetAddress(),
                            client.getClientAddress()
                                  .getCity()
                       )
             );
           } else Notification.show("Please complete the form.");
         });


    form.add(
         firstNameField,
         lastNameField,
         emailField,
         streetAddressField,
         postalCodeField,
         cityField
    );

    var column = new VerticalLayout();
    column.add(form, saveButton);

    return column;
  }

  //@formatter:off
  private void customerBinder() {

    // Bind dos campos de texto aos atributos da classe Customer
    binder
         .forField(firstNameField)
         .asRequired("First name is required")
         .bind(Client::getFirstName, Client::setFirstName);

    binder
         .forField(lastNameField)
         .asRequired("Last name is required")
         .withValidator(
              lastName ->
                   (lastName.length() >= 3 && lastName.length() <= 10),
              "min. 3 characters"
         )
         .bind(Client::getLastName, Client::setLastName);

    binder
         .forField(emailField)
         .asRequired()
         .withValidationStatusHandler(status -> {
           Notification.show("Email is required", 3000, Notification.Position.MIDDLE);
         })
         .bind(Client::getEmail, Client::setEmail);
  }

  private void addressBinder() {

    // BINDER - Style 06: Bind dos campos de texto aos atributos da classe Address
    binder
         .forField(streetAddressField)
         .asRequired("Street address is required")
         .bind("clientAddress.streetAddress");

    // BINDER - Style 07: NOT NULL-SAFETY
    binder.forField(postalCodeField)
          .asRequired("Postal code is required")
          .bind(

               // GETTER => Customer::getAddress -> getPostalCode
               client1 -> client1.getClientAddress()
                                 .getPostalCode(),

               // SETTER => Customer::getAddress -> setPostalCode
               (clientToBind, fieldContent) ->
                    clientToBind.getClientAddress()
                                .setPostalCode(fieldContent)
          );

    // BINDER - Style 07.1: Applying NULL-SAFETY
    // Lambda Custom Validator - Checking Null
    binder
         .forField(cityField)
         .bind(

              // GETTER => Customer::getAddress -> getCity
              client -> {
                return client.getClientAddress() == null
                     ? null
                     : client.getClientAddress()
                             .getCity();
              },

              // Lambda Custom Validator: IF cityField is not BLANK
              // SETTER => Customer::getAddress -> setCity
              (client, fieldContent) -> {
                if (client.getClientAddress() != null)
                  client.getClientAddress()
                        .setCity(fieldContent);

                if (fieldContent.isBlank())
                  client.getClientAddress()
                        .setCity("Check the 'Postal-Code City'");
                else client.getClientAddress()
                           .setCity(fieldContent);
              }
         );
  }
  //@formatter:on
}