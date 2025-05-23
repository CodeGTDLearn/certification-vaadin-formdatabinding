package com.certification.formdatabinding.elevatorsMaintenance.utils.customViewComponents;

import com.vaadin.flow.component.html.Div;

import static com.vaadin.flow.component.notification.Notification.Position;
import static com.vaadin.flow.component.notification.Notification.show;

public class UtilComponents {
  public static Div createDivider() {

    Div divider = new Div();

    divider.getElement().getStyle()
           .set("border-top", "1px solid #ccc")
           .set("width", "100%")
           .set("margin", "10px 0");

    return divider;
  }

  public static void showNotification(String text, Position position) {

    var notification = show(text);
    notification.setPosition(position);
    notification.setDuration(5000); // Duração da notificação em milissegundos
  }
}