package com.certification.formdatabinding.views.formdatabind.practice;

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

@Route("order")
@RouteAlias("")
public class OrderForm extends VerticalLayout {

  private final Binder<Order> binder = new Binder<>(Order.class);

  public OrderForm() {
    // Step 1: Create the form title
    H2 title = new H2("Order Form");
    add(title);

    // Step 2: Create form fields
    TextField customerName = new TextField("Customer Name");
    EmailField customerEmail = new EmailField("Customer Email");
    IntegerField productQuantity = new IntegerField("Quantity");
    Select<String> productCategory = new Select<>();
    productCategory.setLabel("Product Category");
    productCategory.setItems("Electronics", "Clothing", "Food", "Books");
    DatePicker deliveryDate = new DatePicker("Delivery Date");

    // Step 3: Set up form layout
    FormLayout formLayout = new FormLayout();
    formLayout.add(customerName, customerEmail, productQuantity, productCategory, deliveryDate);
    formLayout.setResponsiveSteps(
         new FormLayout.ResponsiveStep("0", 1),
         new FormLayout.ResponsiveStep("500px", 2)
    );
    add(formLayout);

    // Step 4: Configure validations
    binder
         .forField(customerName)
         .withValidator(
              new StringLengthValidator("Name must be between 3 and 50 characters", 3, 50))
         .bind(Order::getCustomerName, Order::setCustomerName);

    binder
         .forField(customerEmail)
         .withValidator(new EmailValidator("Invalid email address"))
         .bind(Order::getCustomerEmail, Order::setCustomerEmail);

    binder
         .forField(productQuantity)
         .withValidator(quantity -> quantity > 0, "Quantity must be greater than zero")
         .bind(Order::getProductQuantity, Order::setProductQuantity);

    binder.forField(productCategory)
          .asRequired("Product category is required")
          .bind(Order::getProductCategory, Order::setProductCategory);

    binder
         .forField(deliveryDate)
         .withValidator(date -> date.isAfter(LocalDate.now()),
                        "Delivery date must be in the future"
         )
         .bind(Order::getDeliveryDate, Order::setDeliveryDate);

    // Step 5: Create submit button and processing logic
    Button submitOrder = new Button("Submit Order", event -> {
      try {
        Order order = new Order();
        binder.writeBean(order);
        processOrder(order);
      }
      catch (ValidationException e) {
        Notification.show("Please correct the errors in the form", 3000,
                          Notification.Position.MIDDLE
        );
      }
    });
    add(submitOrder);
  }

  private void processOrder(Order order) {
    // Simulating order processing
    Notification.show("Order processed successfully!", 3000, Notification.Position.MIDDLE);
  }
}