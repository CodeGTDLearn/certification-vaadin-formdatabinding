package com.certification.formdatabinding.practice_project.serviceTasks;

import com.certification.formdatabinding.practice_project.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.certification.formdatabinding.practice_project.components.CustomComponents.divider;
import static com.certification.formdatabinding.practice_project.config.AppRoutes.TASKS_ROUTE;
import static com.certification.formdatabinding.practice_project.serviceTasks.config.ServiceTasksTitles.*;

@Route(value = TASKS_ROUTE, layout = MainView.class)
public class ServiceTaskView extends VerticalLayout {

  private final Grid<ServiceTask> grid = new Grid<>(ServiceTask.class);
  private final List<ServiceTask> grid_items = new ArrayList<>();
  private final Binder<ServiceTask> binderTask = new Binder<>(ServiceTask.class);

  public ServiceTaskView() {

    H1 viewTitle = new H1(TASKS_VIEW_TITLE);
    H2 formTitle = new H2(TASK_FORM_TITLE);

    var taskField = new TextField("Task");
    var dueDateTaskField = new DatePicker("Due Date");
    var addTaskButton = createAddTaskButton();
    var row = new HorizontalLayout(taskField, dueDateTaskField, addTaskButton);
    row.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
    bind_WithValidators_rowFields(taskField, dueDateTaskField);

    H2 gridTitle = new H2(GRID_TITLE);
    createTaskGrid();

    add(
         viewTitle,
         divider(),
         formTitle,
         row,
         divider(),
         gridTitle,
         grid
    );
  }

  private Button createAddTaskButton() {

    Button button = new Button("Add Task");

    button
         .addClickListener(event -> {

           ServiceTask newTask = new ServiceTask();

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

    // BINDER - Style 04:
    // Permitido pelo 'new Binder<>(Order.class)'
    // Permitido na AUSENCIA de "Validators"
    binderTask.bind(field, ServiceTask::getTask, ServiceTask::setTask);

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

         // BINDER - Style 05:
         // Permitido pelo 'new Binder<>(Order.class)'
         .bind(
              serviceTask -> datePicker.getValue(), // representa o get
              (dateEntity, newDatePicker) -> dateEntity.setDueDate(newDatePicker.toString())
         );
  }

  private void createTaskGrid() {

    grid.setColumns("task", "dueDate", "completed");
    grid.getColumnByKey("completed")
        .setHeader("Status");

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