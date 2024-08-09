package com.certification.formdatabinding.practice_project;

import com.certification.formdatabinding.practice_project.address.AddressView;
import com.certification.formdatabinding.practice_project.customer.CustomerView;
import com.certification.formdatabinding.practice_project.order.OrderView;
import com.certification.formdatabinding.practice_project.tasklist.TaskView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import static com.certification.formdatabinding.practice_project.config.AppMenubarLabels.*;
import static com.certification.formdatabinding.practice_project.config.AppRoutes.PROJECT_ROOT_ROUTE;
import static com.certification.formdatabinding.practice_project.config.AppViewTitles.*;

@PageTitle(CERTIFICATION_LESSON_TITLE)
@Route(value = PROJECT_ROOT_ROUTE)
public class MainView extends AppLayout {

  public MainView() {

    addToNavbar(
         new DrawerToggle(),
         new H2(MAIN_VIEW_TITLE)
    );

    var menuBar = new VerticalLayout();
    menuBar.add(new RouterLink(ORDERS_MENUBAR_LABEL, OrderView.class));
    menuBar.add(new RouterLink(TASKS_MENUBAR_LABEL, TaskView.class));
    menuBar.add(new RouterLink(CUSTOMER_MENUBAR_LABEL, CustomerView.class));
    menuBar.add(new RouterLink(ADDRESS_MENUBAR_LABEL, AddressView.class));

    addToDrawer(menuBar);
  }
}