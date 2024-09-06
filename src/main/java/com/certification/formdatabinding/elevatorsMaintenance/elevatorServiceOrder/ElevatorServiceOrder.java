package com.certification.formdatabinding.elevatorsMaintenance.elevatorServiceOrder;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ElevatorServiceOrder {
  private String customerName;
  private String customerEmail;
  private int elevatorsQuantity;
  private String elevatorCategory;
  private LocalDate deadline;
}