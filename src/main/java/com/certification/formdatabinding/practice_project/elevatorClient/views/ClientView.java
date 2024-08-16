package com.certification.formdatabinding.practice_project.elevatorClient.views;

import com.certification.formdatabinding.practice_project.MainView;
import com.certification.formdatabinding.practice_project.elevatorClient.entity.Client;
import com.certification.formdatabinding.practice_project.elevatorClient.entity.ClientAddress;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import static com.certification.formdatabinding.practice_project.viewComponents.CustomComponents.divider;
import static com.certification.formdatabinding.practice_project.appConfig.AppMessages.APP_MESSAGE_COMPLETE_THE_FORM;
import static com.certification.formdatabinding.practice_project.appConfig.AppRoutes.CLIENTS_ROUTE;
import static com.certification.formdatabinding.practice_project.elevatorClient.config.ClientViewBinderValidationMessages.*;
import static com.certification.formdatabinding.practice_project.elevatorClient.config.ClientViewLabels.*;
import static com.certification.formdatabinding.practice_project.elevatorClient.config.ClientViewTitles.SERVICE_CLIENT_VIEW_FORM_TITLE;
import static com.certification.formdatabinding.practice_project.elevatorClient.config.ClientViewTitles.SERVICE_CLIENT_VIEW_TITLE;

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

    client.setClientAddress(clientAddress);

    H1 viewTitle = new H1(SERVICE_CLIENT_VIEW_TITLE);
    H2 formTitle = new H2(SERVICE_CLIENT_VIEW_FORM_TITLE);

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

  //@formatter:off
  private VerticalLayout createCustomerForm() {

    FormLayout form = new FormLayout();

    firstNameField = new TextField(SERVICE_CLIENT_FIRSTNAME_FIELD_LABEL);
    lastNameField = new TextField(SERVICE_CLIENT_LASTNAME_FIELD_LABEL);
    emailField = new TextField(SERVICE_CLIENT_EMAIL_FIELD_LABEL);

    streetAddressField = new TextField(SERVICE_CLIENT_STREET_FIELD_LABEL);
    postalCodeField = new TextField(SERVICE_CLIENT_POSTALCODE_FIELD_LABEL);
    cityField = new TextField(SERVICE_CLIENT_CITY_FIELD_LABEL);

    Button saveButton = new Button(SERVICE_CLIENT_ADD_CLIENT_BUTTON_LABEL);

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
                            client.getClientAddress().getCity()
                       )
             );
           } else Notification.show(APP_MESSAGE_COMPLETE_THE_FORM);
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


  private void customerBinder() {

    // Bind dos campos de texto aos atributos da classe Customer
    binder
         .forField(firstNameField)
         .asRequired(SERVICE_CLIENT_FIRSTNAME_REQUIRED_MESSAGE)
         .bind(Client::getFirstName, Client::setFirstName);

    binder
         .forField(lastNameField)
         .asRequired(SERVICE_CLIENT_LASTNAME_REQUIRED_MESSSAGE)
         .withValidator(
              lastName ->
                   (lastName.length() >= 3 && lastName.length() <= 10),
              SERVICE_CLIENT_MIN_SIZE_LASTNAME_VALIDATION_MESSSAGE
         )
         .bind(Client::getLastName, Client::setLastName);

    binder
         .forField(emailField)
         .asRequired()
         .withValidationStatusHandler(status -> {
           Notification.show(
                SERVICE_CLIENT_EMAIL_VALIDATION_MESSSAGE,
                3000,
                Notification.Position.MIDDLE
           );
         })
         .bind(Client::getEmail, Client::setEmail);
  }

  private void addressBinder() {

    // BINDER - Style 06: Bind dos campos de texto aos atributos da classe Address
    binder
         .forField(streetAddressField)
         .asRequired(SERVICE_CLIENT_STREET_REQUIRED_MESSSAGE)
         .bind("clientAddress.streetAddress");

    // BINDER - Style 07: NOT NULL-SAFETY
    binder.forField(postalCodeField)
          .asRequired(SERVICE_CLIENT_POSTALCODE_REQUIRED_MESSSAGE)
          .bind(

               // GETTER => Customer::getAddress -> getPostalCode
               client1 -> client1.getClientAddress().getPostalCode(),

               // SETTER => Customer::getAddress -> setPostalCode
               (clientToBind, fieldContent) ->
                    clientToBind.getClientAddress().setPostalCode(fieldContent)
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
                     : client.getClientAddress().getCity();
              },

              // Lambda Custom Validator: IF cityField is not BLANK
              // SETTER => Customer::getAddress -> setCity
              (client, fieldContent) -> {
                if (client.getClientAddress() != null)
                  client.getClientAddress().setCity(fieldContent);

                if (fieldContent.isBlank())
                  client.getClientAddress().setCity("Check the 'Postal-Code City'");
                else client.getClientAddress().setCity(fieldContent);
              }
         );
  }
  //@formatter:on
}