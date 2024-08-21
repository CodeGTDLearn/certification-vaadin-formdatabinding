package com.certification.formdatabinding.practice_project.viewComponents;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;

public class CustomComponents {
  public static Div createDivider() {

    Div divider = new Div();

    divider.getElement().getStyle()
           .set("border-top", "1px solid #ccc")
           .set("width", "100%")
           .set("margin", "10px 0");

    return divider;
  }

  public static void showNotification(String text, Notification.Position position) {
    Notification notification = Notification.show(text);
    notification.setPosition(position);
    notification.setDuration(3000); // Duração da notificação em milissegundos
  }
}