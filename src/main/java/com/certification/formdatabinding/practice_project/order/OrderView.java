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
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.time.LocalDate;

import static com.certification.formdatabinding.practice_project.config.AppRoutes.ORDERS_ROUTE;
import static com.certification.formdatabinding.practice_project.config.AppViewTitles.ORDER_VIEW_TITLE;

@Route(value = ORDERS_ROUTE, layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
public class OrderView extends VerticalLayout {

  private final Binder<Order> binder = new Binder<>(Order.class);
  private TextField nameField;
  private EmailField emailField;
  private IntegerField quantityField;
  private DatePicker deliveryDateField;
  Select<String> categorySelectorField;

  public OrderView() {

    createOrderForm();
    bind_WithValidators_formFields();
  }

  private void createOrderForm() {

    H2 title = new H2(ORDER_VIEW_TITLE);

    nameField = new TextField("Customer Name");
    emailField = new EmailField("Customer Email");
    deliveryDateField = new DatePicker("Delivery Date", LocalDate.now());
    quantityField = new IntegerField("Quantity");
    quantityField.setValue(0);

    categorySelectorField = new Select<>();
    categorySelectorField.setLabel("Product Category");
    categorySelectorField.setItems("Electronics", "Clothing", "Food", "Books");

    var formLayout = new FormLayout();

    formLayout.add(
         title,
         nameField,
         emailField,
         quantityField,
         categorySelectorField,
         deliveryDateField
    );

    formLayout
         .setResponsiveSteps(
              new FormLayout.ResponsiveStep("0", 1),
              new FormLayout.ResponsiveStep("500px", 2)
         );

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

  private void bind_WithValidators_formFields() {

    binder
         // Style 01:
         // Permitido pelo 'new Binder<>(Order.class)'
         // Permitido na AUSENCIA de "Validators"
         .bind(nameField, "customerName");

    binder
         .forField(emailField)
         .withValidator(new EmailValidator("Invalid email address"))

         // Style 02:
         // Permitido pelo 'new Binder<>(Order.class)'
         .bind("customerEmail");

    binder
         .forField(quantityField)
         .withValidator(quantity -> quantity > 0, "must be greater than zero")

         // Style 03:
         // Permitido pelo 'new Binder<>(Order.class)' or 'new Binder<>()'
         .bind(Order::getProductQuantity, Order::setProductQuantity);

    binder.bind(
         categorySelectorField,
         order -> order.getProductCategory(),
         (oldCategory, newCategory) -> oldCategory.setProductCategory(newCategory)
    );

    binder
         .forField(deliveryDateField)
         .asRequired("Delivery Date required")
         .withValidator(
              date -> date.isAfter(LocalDate.now()),
              "Delivery date must be in the future"
         )
         .bind("deliveryDate");
  }

  private void processOrder(Order order) {
    // Simulating order processing
    final String text =
         "Order processed %s - Quantity %s!"
              .formatted(
                   nameField.getValue(),
                   quantityField.getValue()
                                .toString()
              );

    Notification.show(text, 3000, Notification.Position.MIDDLE);
  }
}