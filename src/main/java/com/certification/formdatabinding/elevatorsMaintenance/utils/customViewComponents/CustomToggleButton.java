package com.certification.formdatabinding.elevatorsMaintenance.utils.customViewComponents;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dependency.JsModule;

public class CustomToggleButton extends CustomField<Boolean> {

  private  ToggleButton button = new ToggleButton();

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

  @JsModule("@polymer/paper-toggle-button/paper-toggle-button.js")
  @Tag("paper-toggle-button")
  private static class ToggleButton extends AbstractSinglePropertyField<ToggleButton, Boolean> {

    public ToggleButton() {

      super("checked", false, false);
    }
  }

}