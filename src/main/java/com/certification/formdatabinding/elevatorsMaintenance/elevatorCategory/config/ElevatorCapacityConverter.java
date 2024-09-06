package com.certification.formdatabinding.elevatorsMaintenance.elevatorCategory.config;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import static com.certification.formdatabinding.elevatorsMaintenance.elevatorCategory.config.ElevatorCategoryMessages.ELEVATOR_CAPACITY_CUSTOM_CONVERTER_MESSAGE;

// CONVERTER  - Style 2.0: Custom Converter
// - 'convertToPresentation': Converte Bean-Attribute -> ViewField
// - 'convertToModel'       : Converte ViewField      -> Bean-Attribute
public class ElevatorCapacityConverter implements Converter<String, Integer> {

  @Override
  public Result<Integer> convertToModel(String fieldValue, ValueContext context) {

    try {
      // Remove any text, leaving only the numbers
      return Result.ok(Integer.parseInt(fieldValue));
    }
    catch (NumberFormatException e) {
      return Result.error(ELEVATOR_CAPACITY_CUSTOM_CONVERTER_MESSAGE);
    }
  }

  @Override
  public String convertToPresentation(Integer modelAttribute, ValueContext context) {

    if (modelAttribute == 1) return modelAttribute + " personY";
    if (modelAttribute > 1) return modelAttribute + " peopleX";
    return "";
  }

}