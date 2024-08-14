package com.certification.formdatabinding.practice_project.serviceTasks;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceTask {
  private String task;
  private String dueDate;
  private boolean completed;
}