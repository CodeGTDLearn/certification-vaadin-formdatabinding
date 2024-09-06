package com.certification.formdatabinding.elevatorsMaintenance;

import com.certification.formdatabinding.elevatorsMaintenance.elevatorCategory.ElevatorCategoryView;
import com.certification.formdatabinding.elevatorsMaintenance.elevatorClient.views.ElevatorClientAddressView;
import com.certification.formdatabinding.elevatorsMaintenance.elevatorClient.views.ElevatorClientView;
import com.certification.formdatabinding.elevatorsMaintenance.elevatorParts.ElevatorPartsView;
import com.certification.formdatabinding.elevatorsMaintenance.elevatorRepairTasks.ElevatorRepairTaskView;
import com.certification.formdatabinding.elevatorsMaintenance.elevatorServiceOrder.ElevatorServiceOrderView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import static com.certification.formdatabinding.elevatorsMaintenance.config.AppMenubarItems.*;
import static com.certification.formdatabinding.elevatorsMaintenance.config.AppRoutes.ROOT_ROUTE;
import static com.certification.formdatabinding.elevatorsMaintenance.config.AppTitles.APP_MAIN_VIEW_TITLE;
import static com.certification.formdatabinding.elevatorsMaintenance.config.AppTitles.CERTIFICATION_LESSON_TITLE;

@PageTitle(CERTIFICATION_LESSON_TITLE)
@Route(value = ROOT_ROUTE)
public class MainView extends AppLayout {

  public MainView() {

    addToNavbar(
         new DrawerToggle(),
         new H2(APP_MAIN_VIEW_TITLE)
    );

    var menuBar = new VerticalLayout();
    menuBar.add(new RouterLink(ORDERS_MENU_ITEM, ElevatorServiceOrderView.class));
    menuBar.add(new RouterLink(REPAIR_TASKS_MENU_ITEM, ElevatorRepairTaskView.class));
    menuBar.add(new RouterLink(CLIENTS_MENU_ITEM, ElevatorClientView.class));
    menuBar.add(new RouterLink(CLIENTS_ADDRESS_MENU_ITEM, ElevatorClientAddressView.class));
    menuBar.add(new RouterLink(ELEVATOR_CATEGORY_MENU_ITEM, ElevatorCategoryView.class));
    menuBar.add(new RouterLink(ELEVATOR_PARTS_MENU_ITEM, ElevatorPartsView.class));

    addToDrawer(menuBar);
  }
}