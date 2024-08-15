package com.certification.formdatabinding.practice_project;

import com.certification.formdatabinding.practice_project.elevatorCategory.ElevatorCategoryView;
import com.certification.formdatabinding.practice_project.repairTasks.RepairTaskView;
import com.certification.formdatabinding.practice_project.serviceClient.views.ClientAddressView;
import com.certification.formdatabinding.practice_project.serviceClient.views.ClientView;
import com.certification.formdatabinding.practice_project.serviceOrder.ServiceOrderView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import static com.certification.formdatabinding.practice_project.config.AppRoutes.ROOT_ROUTE;
import static com.certification.formdatabinding.practice_project.config.AppTitles.APP_MAIN_VIEW_TITLE;
import static com.certification.formdatabinding.practice_project.config.AppTitles.CERTIFICATION_LESSON_TITLE;
import static com.certification.formdatabinding.practice_project.config.MenuItems.*;

@PageTitle(CERTIFICATION_LESSON_TITLE)
@Route(value = ROOT_ROUTE)
public class MainView extends AppLayout {

  public MainView() {

    addToNavbar(
         new DrawerToggle(),
         new H2(APP_MAIN_VIEW_TITLE)
    );

    var menuBar = new VerticalLayout();
    menuBar.add(new RouterLink(ORDERS_MENU_ITEM, ServiceOrderView.class));
    menuBar.add(new RouterLink(REPAIR_TASKS_MENU_ITEM, RepairTaskView.class));
    menuBar.add(new RouterLink(CLIENTS_MENU_ITEM, ClientView.class));
    menuBar.add(new RouterLink(CLIENTS_ADDRESS_MENU_ITEM, ClientAddressView.class));
    menuBar.add(new RouterLink(ELEVATOR_CATEGORY_MENU_ITEM, ElevatorCategoryView.class));

    addToDrawer(menuBar);
  }
}