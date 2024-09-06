package com.certification.formdatabinding.elevatorsMaintenance.utils.customViewComponents.paper_toggle_button;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.Tag;

@JsModule("@polymer/paper-toggle-button/paper-toggle-button.js")
@Tag("paper-toggle-button")
public class PolymerToggleButton extends AbstractSinglePropertyField<PolymerToggleButton, Boolean> {

  public PolymerToggleButton() {

    super("checked", false, false);
  }
}