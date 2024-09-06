package com.certification.formdatabinding.elevatorsMaintenance.utils.customViewComponents;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;

import java.time.LocalDateTime;

public class CustomDataPicker extends CustomField<LocalDateTime> {

  private DatePicker datePicker = new DatePicker();
  private TimePicker timePicker = new TimePicker();
  private Div splitter = new Div();


  public CustomDataPicker() {

    splitter.getStyle().set("border-left", "2px solid red");

    var row = new HorizontalLayout();
    row.add(datePicker, splitter, timePicker);
    row.setSpacing(false);

    add(row);

  }

  @Override
  protected LocalDateTime generateModelValue() {

    return LocalDateTime.of(datePicker.getValue(),timePicker.getValue());
  }

  @Override
  protected void setPresentationValue(LocalDateTime newPresentationValue) {

    datePicker.setValue(newPresentationValue.toLocalDate());
    timePicker.setValue(newPresentationValue.toLocalTime());
  }


}