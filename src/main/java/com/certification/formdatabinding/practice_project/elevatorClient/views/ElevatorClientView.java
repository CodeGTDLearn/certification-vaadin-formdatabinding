package com.certification.formdatabinding.practice_project.elevatorClient.views;

import com.certification.formdatabinding.practice_project.MainView;
import com.certification.formdatabinding.practice_project.elevatorClient.entity.ElevatorClient;
import com.certification.formdatabinding.practice_project.elevatorClient.entity.ElevatorClientAddress;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import static com.certification.formdatabinding.practice_project.viewComponents.CustomComponents.createDivider;
import static com.certification.formdatabinding.practice_project.appConfig.AppMessages.APP_MESSAGE_COMPLETE_THE_FORM;
import static com.certification.formdatabinding.practice_project.appConfig.AppRoutes.CLIENTS_ROUTE;
import static com.certification.formdatabinding.practice_project.elevatorClient.config.ElevatorClientMessages.*;
import static com.certification.formdatabinding.practice_project.elevatorClient.config.ElevatorClientViewLabels.*;
import static com.certification.formdatabinding.practice_project.elevatorClient.config.ElevatorClientViewTitles.SERVICE_CLIENT_VIEW_FORM_TITLE;
import static com.certification.formdatabinding.practice_project.elevatorClient.config.ElevatorClientViewTitles.SERVICE_CLIENT_VIEW_TITLE;

@Route(value = CLIENTS_ROUTE, layout = MainView.class)
public class ElevatorClientView extends VerticalLayout {

  private final ElevatorClient elevatorClient = new ElevatorClient();
  private final ElevatorClientAddress elevatorClientAddress = new ElevatorClientAddress();

  private Binder<ElevatorClient> binder = new Binder<>(ElevatorClient.class);

  private TextField firstNameField;
  private TextField lastNameField;
  private TextField emailField;

  private TextField streetAddressField;
  private TextField postalCodeField;
  private TextField cityField;

  public ElevatorClientView() {

    elevatorClient.setElevatorClientAddress(elevatorClientAddress);

    H1 viewTitle = new H1(SERVICE_CLIENT_VIEW_TITLE);
    H2 formTitle = new H2(SERVICE_CLIENT_VIEW_FORM_TITLE);

    var customerForm = createForm();

    settingAddressBinder();

    settingCustomerBinder();

    add(
         viewTitle,
         createDivider(),
         formTitle,
         customerForm
    );
  }

  //@formatter:off
  private VerticalLayout createForm() {

    var form = new FormLayout();

    firstNameField = new TextField(FIRSTNAME_FIELD_LABEL);
    lastNameField = new TextField(LASTNAME_FIELD_LABEL);
    emailField = new TextField(EMAIL_FIELD_LABEL);

    streetAddressField = new TextField(STREET_FIELD_LABEL);
    postalCodeField = new TextField(POSTALCODE_FIELD_LABEL);
    cityField = new TextField(CITY_FIELD_LABEL);

    var saveButton = new Button(ADD_BUTTON_LABEL);

    saveButton
         .addClickListener(event -> {
           if (binder.writeBeanIfValid(elevatorClient)) {
             Notification.show(
                  "Data Saved: %s %n %s %n %s %n %s %n %s"
                       .formatted(
                            elevatorClient.getFirstName(),
                            elevatorClient.getLastName(),
                            elevatorClient.getEmail(),
                            elevatorClient.getElevatorClientAddress().getStreetAddress(),
                            elevatorClient.getElevatorClientAddress().getCity()
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

  private void settingCustomerBinder() {

    // BINDER: Bind dos campos de texto aos atributos da classe Customer
    binder
         .forField(firstNameField)
         .asRequired(SERVICE_CLIENT_FIRSTNAME_REQUIRED_MESSAGE)
         .bind(ElevatorClient::getFirstName, ElevatorClient::setFirstName);

    // VALIDATOR: Lambda
    binder
         .forField(lastNameField)
         .asRequired(SERVICE_CLIENT_LASTNAME_REQUIRED_MESSSAGE)
         .withValidator(
              lastName ->
                   (lastName.length() >= 3 && lastName.length() <= 10),
              SERVICE_CLIENT_MIN_SIZE_LASTNAME_VALIDATION_MESSSAGE
         )
         .bind(ElevatorClient::getLastName, ElevatorClient::setLastName);

    // VALIDATOR: StatusHandler
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
         .bind(ElevatorClient::getEmail, ElevatorClient::setEmail);
  }

  private void settingAddressBinder() {

    // BINDER: Bind dos campos de texto aos atributos da classe Address
    binder
         .forField(streetAddressField)
         .asRequired(SERVICE_CLIENT_STREET_REQUIRED_MESSSAGE)
         .bind("clientAddress.streetAddress");

    // BINDER: NOT NULL-SAFETY
    binder.forField(postalCodeField)
          .asRequired(SERVICE_CLIENT_POSTALCODE_REQUIRED_MESSSAGE)
          .bind(

               // GETTER => Customer::getAddress -> getPostalCode
               elevatorClient1 -> elevatorClient1.getElevatorClientAddress().getPostalCode(),

               // SETTER => Customer::getAddress -> setPostalCode
               (elevatorClientToBind, fieldContent) ->
                    elevatorClientToBind.getElevatorClientAddress().setPostalCode(fieldContent)
          );

    // BINDER: Applying NULL-SAFETY -Lambda Double Validator - Checking Null
    binder
         .forField(cityField)
         .bind(

              // GETTER => Customer::getAddress -> getCity
              elevatorClient -> {
                return elevatorClient.getElevatorClientAddress() == null
                     ? null
                     : elevatorClient.getElevatorClientAddress().getCity();
              },

              // Lambda Custom Validator: IF cityField is not BLANK
              // SETTER => Customer::getAddress -> setCity
              (elevatorClient, fieldContent) -> {
                if (elevatorClient.getElevatorClientAddress() != null)
                  elevatorClient.getElevatorClientAddress().setCity(fieldContent);

                if (fieldContent.isBlank())
                  elevatorClient.getElevatorClientAddress().setCity("Check the 'Postal-Code City'");
                else elevatorClient.getElevatorClientAddress().setCity(fieldContent);
              }
         );
  }
  //@formatter:on
}