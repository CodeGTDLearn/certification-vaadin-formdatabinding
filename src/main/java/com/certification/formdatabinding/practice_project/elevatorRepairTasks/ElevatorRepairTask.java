package com.certification.formdatabinding.practice_project.elevatorRepairTasks;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ElevatorRepairTask {
  private String task;
  private String dueDate;
  private boolean completed;
}