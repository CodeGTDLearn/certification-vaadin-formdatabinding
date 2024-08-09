package com.certification.formdatabinding.practice_project.tasklist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskItem {
  private String task;
  private String dueDate;
  private boolean completed;
}