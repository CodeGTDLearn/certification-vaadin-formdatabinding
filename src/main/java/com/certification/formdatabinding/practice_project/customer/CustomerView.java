package com.certification.formdatabinding.practice_project.customer;

import com.certification.formdatabinding.practice_project.MainView;
import com.certification.formdatabinding.practice_project.address.Address;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import static com.certification.formdatabinding.practice_project.config.AppRoutes.CUSTOMER_VIEW_ROUTE;
import static com.certification.formdatabinding.practice_project.config.AppViewTitles.CUSTOMER_VIEW_TITLE;

@Route(value = CUSTOMER_VIEW_ROUTE, layout = MainView.class)
public class CustomerView extends VerticalLayout {

  private final Customer customer = new Customer();
  private final Address address = new Address();

  private Binder<Customer> binder = new Binder<>(Customer.class);

  private TextField firstNameField;
  private TextField lastNameField;
  private TextField emailField;

  private TextField streetAddressField;
  private TextField postalCodeField;
  private TextField cityField;

  public CustomerView() {

    customer.setAddress(address);


    H2 viewTitle = new H2(CUSTOMER_VIEW_TITLE);
    var customerForm = createCustomerForm();

    addressBinder();
    customerBinder();

    add(viewTitle, customerForm);
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
           if (binder.writeBeanIfValid(customer)) {
             Notification.show(
                  "Data Saved: %s %n %s %n %s %n %s %n %s"
                       .formatted(
                            customer.getFirstName(),
                            customer.getLastName(),
                            customer.getEmail(),
                            customer.getAddress()
                                    .getStreetAddress(),
                            customer.getAddress()
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
         .bind(Customer::getFirstName, Customer::setFirstName);

    binder
         .forField(lastNameField)
         .asRequired("Last name is required")
         .withValidator(lastName -> lastName.length() >= 3, "min. 3 characters")
         .bind(Customer::getLastName, Customer::setLastName);

    binder
         .forField(emailField)
         .asRequired()
         .withValidationStatusHandler(status -> {
           Notification.show("Email is required", 3000, Notification.Position.MIDDLE);
         })
         .bind(Customer::getEmail, Customer::setEmail);
  }

  private void addressBinder() {

    // Style 06:
    // Bind dos campos de texto aos atributos da classe Address
    binder
         .forField(streetAddressField)
         .asRequired("Street address is required")
         .bind("address.streetAddress");

    // Style 07: NOT NULL-SAFETY
    binder.forField(postalCodeField)
          .asRequired("Postal code is required")
          .bind(

               // GETTER => Customer::getAddress -> getPostalCode
               customer1 -> customer1.getAddress().getPostalCode(),

               // SETTER => Customer::getAddress -> setPostalCode
               (customerToBind, fieldContent) ->
                    customerToBind.getAddress().setPostalCode(fieldContent)
          );

    // Style 07.1: Applying NULL-SAFETY
    // Lambda Custom Validator - Checking Null
    binder
         .forField(cityField)
         .bind(

              // GETTER => Customer::getAddress -> getCity
              customer -> {
                return customer.getAddress() == null
                     ? null
                     : customer.getAddress().getCity();
              },

              // Lambda Custom Validator: IF cityField is not BLANK
              // SETTER => Customer::getAddress -> setCity
              (customer, fieldContent) -> {
                if (customer.getAddress() != null)
                  customer.getAddress().setCity(fieldContent);

                if (fieldContent.isBlank())
                  customer.getAddress().setCity("Check the 'Postal-Code City'");
                else customer.getAddress().setCity(fieldContent);
              }
         );
  }
  //@formatter:on
}