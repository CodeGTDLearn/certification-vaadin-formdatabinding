package com.certification.formdatabinding.elevatorsMaintenance.elevatorRepairTasks;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ElevatorRepairTask {
  private String task;
  private String dueDate;
  private boolean completed;
}