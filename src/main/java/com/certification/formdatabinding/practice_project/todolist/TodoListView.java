package com.certification.formdatabinding.practice_project.todolist;

import com.certification.formdatabinding.practice_project.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.certification.formdatabinding.practice_project.config.AppRoutes.TODO_LIST_ROUTE;

@Route(value = TODO_LIST_ROUTE, layout = MainView.class)
public class TodoListView extends VerticalLayout {

  private final List<TodoItem> todoItems = new ArrayList<>();
  private final Grid<TodoItem> grid = new Grid<>(TodoItem.class);
  private final Binder<TodoItem> binder = new Binder<>(TodoItem.class);

  public TodoListView() {

    var taskField = new TextField("Task");
    var dueDateTaskField = new DatePicker("Due Date");
    var addTaskButton = createAddTaskButton();

    var row = new HorizontalLayout(taskField, dueDateTaskField, addTaskButton);
    row.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

    applyBinderInTheRowFields(taskField, dueDateTaskField);

    createGrid();

    add(row, grid);
  }

  private Button createAddTaskButton() {
    Button button = new Button("Add Task");

    button
         .addClickListener(event -> {

           TodoItem newItem = new TodoItem();

           if (binder.writeBeanIfValid(newItem)) {
             todoItems.add(newItem);
             grid.setItems(todoItems);
             binder.readBean(new TodoItem());
           }
         });

    return button;
  }

  private void applyBinderInTheRowFields(TextField taskField, DatePicker dueDatePicker) {

    // Step 3: Configure the binder
    binder.forField(taskField)
          .withValidator(
               task -> task.length() >= 3,
               "Task must be at least 3 characters long"
          )
          .bind(TodoItem::getTask, TodoItem::setTask);

    binder
         .forField(dueDatePicker)
         .withValidator(
              date -> date != null,
              "Due date is required"
         )
         .withValidator(
              date -> date.isAfter(LocalDate.now()) || date.isEqual(LocalDate.now()),
              "Due date must be today or in the future"
         )
         .bind(
              todo -> dueDatePicker.getValue(),
              (todo, value) -> todo.setDueDate(value.toString())
         );
  }

  private void createGrid() {
    // Step 2: Set up the grid
    grid.setColumns("task", "dueDate", "completed");
    grid.getColumnByKey("completed")
        .setHeader("Status");

    // Step 5: Add checkbox to grid
    grid.addComponentColumn(item -> {
          Checkbox checkbox = new Checkbox(item.isCompleted());
          checkbox.addValueChangeListener(event -> {
            item.setCompleted(event.getValue());
            grid.getDataProvider()
                .refreshItem(item);
          });
          return checkbox;
        })
        .setHeader("Complete");
  }
}