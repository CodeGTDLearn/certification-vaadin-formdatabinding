package com.certification.formdatabinding.practice_project.address;

import com.certification.formdatabinding.practice_project.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.PropertyId;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

import static com.certification.formdatabinding.practice_project.config.AppRoutes.ADDRESS_VIEW_ROUTE;
import static com.certification.formdatabinding.practice_project.config.AppViewTitles.ADDRESS_VIEW_TITLE;

@Route(value = ADDRESS_VIEW_ROUTE, layout = MainView.class)
public class AddressView extends VerticalLayout {

  //  Automatic Binding by Entity-Attribute name
  private TextField streetAddress = new TextField();

  @PropertyId("city")
  private TextField cityField = new TextField();

  //  Automatic Binding by Entity-Attribute name
  private TextField postalCode = new TextField();

  private Binder<Address> binder = new Binder<>(Address.class);

  private final Grid<Address> grid = new Grid<>(Address.class);

  private List<Address> listAdress = new ArrayList<>();
  private Address address = new Address();

  public AddressView() {

    binder.bindInstanceFields(this);

    grid.setColumns("streetAddress", "city", "postalCode");
    var customerAddressForm = customerAddressForm();

    add(customerAddressForm, grid);
  }

  private VerticalLayout customerAddressForm() {

    H2 viewTitle = new H2(ADDRESS_VIEW_TITLE);

    var form = new FormLayout();
    streetAddress.setLabel("Street Address");
    postalCode.setLabel("Postal Code");
    cityField.setLabel("City");
    form.add(streetAddress, postalCode, cityField);

    var saveButton = new Button("Save");

    saveButton
         .addClickListener(clickEvent -> {

           if (binder.writeBeanIfValid(address)) {

             listAdress.add(address);
             grid.setItems(listAdress);

             var sucessSaving = "Saved Data: %s %n %s %n %s."
                  .formatted(address.getStreetAddress(), address.getCity(),
                             address.getPostalCode()
                  );
             Notification.show(sucessSaving);
           } else Notification.show("Please complete the form.");
         });

    var column = new VerticalLayout();
    column.add(viewTitle, form, saveButton);

    return column;
  }
}