package com.certification.formdatabinding.practice_project;

import com.certification.formdatabinding.practice_project.order.OrderView;
import com.certification.formdatabinding.practice_project.todolist.TodoListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import static com.certification.formdatabinding.practice_project.config.AppRoutes.PROJECT_ROOT_ROUTE;
import static com.certification.formdatabinding.practice_project.config.AppTitles.*;

@PageTitle(CERTIFICATION_LESSON_TITLE)
@Route(value = PROJECT_ROOT_ROUTE)
public class MainView extends AppLayout {

  public MainView() {

    addToNavbar(
         new DrawerToggle(),
         new H2(MAIN_VIEW_TITLE)
    );

    var menuBar = new VerticalLayout();
    menuBar.add(new RouterLink(ORDER_MENU_TITLE, OrderView.class));
    menuBar.add(new RouterLink(TODOLIST_MENU_TITLE, TodoListView.class));

    addToDrawer(menuBar);
  }
}