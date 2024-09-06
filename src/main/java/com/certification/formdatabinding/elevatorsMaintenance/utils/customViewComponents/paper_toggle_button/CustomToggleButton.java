package com.certification.formdatabinding.elevatorsMaintenance.utils.customViewComponents.paper_toggle_button;

import com.vaadin.flow.component.customfield.CustomField;

public class CustomToggleButton extends CustomField<Boolean> {

  private PolymerToggleButton button = new PolymerToggleButton();

  public CustomToggleButton() {

    add(button);
  }

  @Override
  protected Boolean generateModelValue() {

    return button.getValue();
  }

  @Override
  protected void setPresentationValue(Boolean newPresentationValue) {

    button.setValue(newPresentationValue);
  }
}