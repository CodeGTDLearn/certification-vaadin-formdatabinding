package com.certification.formdatabinding.elevatorsMaintenance.utils.customViewComponents.my_sec_comp;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;

@JsModule("@material/web/checkbox/checkbox.js")
@Tag("checkbox")
public class MaterialSwitch extends AbstractSinglePropertyField<MaterialSwitch, Boolean> {

  public MaterialSwitch() {

    super("checked", false, false);
  }
}