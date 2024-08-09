package com.certification.formdatabinding.practice_project.tasklist;

import com.certification.formdatabinding.practice_project.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.certification.formdatabinding.practice_project.config.AppRoutes.TODO_LIST_ROUTE;
import static com.certification.formdatabinding.practice_project.config.AppViewTitles.TASK_VIEW_TITLE;

@Route(value = TODO_LIST_ROUTE, layout = MainView.class)
public class TaskView extends VerticalLayout {

  private final Grid<TaskItem> grid = new Grid<>(TaskItem.class);
  private final List<TaskItem> grid_items = new ArrayList<>();
  private final Binder<TaskItem> binderTask = new Binder<>(TaskItem.class);

  public TaskView() {


    H2 title = new H2(TASK_VIEW_TITLE);

    var taskField = new TextField("Task");
    var dueDateTaskField = new DatePicker("Due Date");
    var addTaskButton = createAddTaskButton();

    var row = new HorizontalLayout(taskField, dueDateTaskField, addTaskButton);
    row.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

    bind_WithValidators_rowFields(taskField, dueDateTaskField);

    createTaskGrid();

    add(title, row, grid);
  }

  private Button createAddTaskButton() {

    Button button = new Button("Add Task");

    button
         .addClickListener(event -> {

           TaskItem newTask = new TaskItem();

           // binderTask [writeBeanIfValid]:
           // - check Validation of AllFields +
           // - 'write' "Field-Content" para "Entity-Attribute"
           if (binderTask.writeBeanIfValid(newTask)) {
             grid_items.add(newTask);
             grid.setItems(grid_items);
           }
         });

    return button;
  }

  private void bind_WithValidators_rowFields(TextField field, DatePicker datePicker) {

    // Style 04:
    // Permitido pelo 'new Binder<>(Order.class)'
    // Permitido na AUSENCIA de "Validators"
    binderTask.bind(field, TaskItem::getTask, TaskItem::setTask);

    // binderTask [forField/bind]: "Link" between "field" -> Entity_Attribute
    binderTask
         .forField(datePicker)
         .withValidator(
              date -> date != null,
              "Due date is required"
         )
         .withValidator(
              date -> date.isAfter(LocalDate.now()) || date.isEqual(LocalDate.now()),
              "Due date must be today or in the future"
         )

         // Style 05:
         // Permitido pelo 'new Binder<>(Order.class)'
         .bind(
              taskItem -> datePicker.getValue(), // representa o get
              (dateEntity, newDatePicker) -> dateEntity.setDueDate(newDatePicker.toString())
         );
  }

  private void createTaskGrid() {

    // Step 2: Set up the grid
    grid.setColumns("task", "dueDate", "completed");
    grid.getColumnByKey("completed")
        .setHeader("Status");

    // Step 5: Add checkbox to grid
    grid
         .addComponentColumn(columnItem -> {

           var taskCompleted_checkbox = new Checkbox(columnItem.isCompleted());

           taskCompleted_checkbox
                .addValueChangeListener(
                     checkboxState -> {

                       columnItem
                            .setCompleted(checkboxState.getValue());

                       grid
                            .getDataProvider()
                            .refreshItem(columnItem);
                     });
           return taskCompleted_checkbox;
         })
         .setHeader("Completed");
  }
}