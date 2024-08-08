package com.certification.formdatabinding.practice_project.todolist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoItem {
  private String task;
  private String dueDate;
  private boolean completed;
}