package com.certification.formdatabinding.elevatorsMaintenance.elevatorCategory.config;

import lombok.Getter;

@Getter
public enum ElevatorCategoryTypes {
  LUX("Lux"),
  SERVICE("Service"),
  PANORAMIC("Panoramic");

  private final String name;

  ElevatorCategoryTypes(String name) {

    this.name = name;
  }

  @Override
  public String toString() {

    return name;
  }
}