package com.certification.formdatabinding.elevatorsMaintenance.utils.customViewComponents.my_sec_comp;

import com.vaadin.flow.component.customfield.CustomField;

public class CustomMaterialSwitch extends CustomField<Boolean> {

  private MaterialSwitch materialSwitch = new MaterialSwitch();

  public CustomMaterialSwitch() {

    add(materialSwitch);
  }

  @Override
  protected Boolean generateModelValue() {

    return materialSwitch.getValue();
  }

  @Override
  protected void setPresentationValue(Boolean newPresentationValue) {

    materialSwitch.setValue(newPresentationValue);
  }
}