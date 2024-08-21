package com.certification.formdatabinding.practice_project.elevatorParts;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.certification.formdatabinding.practice_project.elevatorParts.config.ElevatorPartsMessages.MIN_QUANTITY_MESSSAGE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElevatorParts {

  private String partName;
  private String manufacturer;
  private String description;

  @Min(value=3,message =MIN_QUANTITY_MESSSAGE)
  private Integer quantity;

}