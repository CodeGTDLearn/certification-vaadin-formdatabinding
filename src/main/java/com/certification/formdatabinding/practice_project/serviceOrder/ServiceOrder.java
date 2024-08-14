package com.certification.formdatabinding.practice_project.serviceOrder;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ServiceOrder {
  private String customerName;
  private String customerEmail;
  private int elevatorsQuantity;
  private String elevatorCategory;
  private LocalDate deadline;
}