package com.certification.formdatabinding.practice_project.elevatorParts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ElevatorParts {

  private String partId;
  private String partName;
  private int quantity;
  private String manufacturer;

}