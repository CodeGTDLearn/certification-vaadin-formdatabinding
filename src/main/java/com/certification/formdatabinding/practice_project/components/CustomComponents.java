package com.certification.formdatabinding.practice_project.components;

import com.vaadin.flow.component.html.Div;

public class CustomComponents {
  public static Div divider() {

    Div divider = new Div();

    divider.getElement().getStyle()
           .set("border-top", "1px solid #ccc")
           .set("width", "100%")
           .set("margin", "10px 0");

    return divider;
  }
}