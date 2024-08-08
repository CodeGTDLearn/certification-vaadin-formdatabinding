package com.certification.formdatabinding.practice_project.order;

import com.certification.formdatabinding.practice_project.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.time.LocalDate;

import static com.certification.formdatabinding.practice_project.config.AppRoutes.ORDERS_ROUTE;

@Route(value = ORDERS_ROUTE, layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
public class OrderView extends VerticalLayout {

  private final Binder<Order> binder = new Binder<>(Order.class);
  private TextField nameField;
  private EmailField emailField;
  private IntegerField quantityField;
  private DatePicker deliveryDateField;
  Select<String> categorySelector;

  public OrderView() {
    createOrderForm();
    applyBinderInOrderForm();
  }

  private void createOrderForm() {

    H2 title = new H2("Order Form");
    nameField = new TextField("Customer Name");
    emailField = new EmailField("Customer Email");
    deliveryDateField = new DatePicker("Delivery Date", LocalDate.now());
    quantityField = new IntegerField("Quantity");
    quantityField.setValue(0);

    categorySelector = new Select<>();
    categorySelector.setLabel("Product Category");
    categorySelector.setItems("Electronics", "Clothing", "Food", "Books");

    var formLayout = new FormLayout();

    formLayout.add(
         title,
         nameField,
         emailField,
         quantityField,
         categorySelector,
         deliveryDateField
    );

    formLayout
         .setResponsiveSteps(
              new FormLayout.ResponsiveStep("0", 1),
              new FormLayout.ResponsiveStep("500px", 2)
         );

    // Step 5: Create submit button and processing logic
    Button submitOrder = new Button("Submit Order", event -> {
      try {
        Order order = new Order();
        binder.writeBean(order);
        processOrder(order);
      }
      catch (ValidationException e) {
        Notification.show(
             "Please correct the errors in the form",
             3000,
             Notification.Position.MIDDLE
        );
      }
    });

    add(formLayout, submitOrder);
  }

  private void applyBinderInOrderForm() {

    // Step 4: Configure validations
    binder
         .forField(nameField)
         .withValidator(
              new StringLengthValidator(
                   "Name must be between 3 and 50 characters",
                   3,
                   50
              )
         )
         .bind(Order::getCustomerName, Order::setCustomerName);

    binder
         .forField(emailField)
         .withValidator(new EmailValidator("Invalid email address"))
         .bind(Order::getCustomerEmail, Order::setCustomerEmail);

    binder
         .forField(quantityField)
         .withValidator(quantity -> quantity > 0, "Quantity must be greater than zero")
         .bind(Order::getProductQuantity, Order::setProductQuantity);

    binder
         .forField(categorySelector)
         .asRequired("Product category is required")
         .bind(Order::getProductCategory, Order::setProductCategory);

    binder
         .forField(deliveryDateField)
         .withValidator(
              date -> date.isAfter(LocalDate.now()),
              "Delivery date must be in the future"
         )
         .bind(Order::getDeliveryDate, Order::setDeliveryDate);
  }

  private void processOrder(Order order) {
    // Simulating order processing
    final String text = "Order processed successfully!";

    Notification.show(text, 3000, Notification.Position.MIDDLE);
  }
}