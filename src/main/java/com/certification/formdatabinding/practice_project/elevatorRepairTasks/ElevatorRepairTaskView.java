package com.certification.formdatabinding.practice_project.elevatorRepairTasks;

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

import static com.certification.formdatabinding.practice_project.viewComponents.CustomComponents.createDivider;
import static com.certification.formdatabinding.practice_project.appConfig.AppRoutes.REPAIR_TASKS;
import static com.certification.formdatabinding.practice_project.elevatorRepairTasks.config.ElevatorRepairTasksLabels.*;
import static com.certification.formdatabinding.practice_project.elevatorRepairTasks.config.ElevatorRepairTasksTitles.*;
import static com.certification.formdatabinding.practice_project.elevatorRepairTasks.config.ElevatorRepairTasksValidationMessages.REPAIR_TASK_DUEDATE_REQUIRED_MESSAGE;
import static com.certification.formdatabinding.practice_project.elevatorRepairTasks.config.ElevatorRepairTasksValidationMessages.REPAIR_TASK_DUEDATE_VALIDATION_MESSAGE;

@Route(value = REPAIR_TASKS, layout = MainView.class)
public class ElevatorRepairTaskView extends VerticalLayout {

  private final Grid<ElevatorRepairTask> gridItemsView = new Grid<>(ElevatorRepairTask.class);
  private final List<ElevatorRepairTask> gridItemsList = new ArrayList<>();
  private final Binder<ElevatorRepairTask> binderTask = new Binder<>(ElevatorRepairTask.class);

  public ElevatorRepairTaskView() {

    H1 viewTitle = new H1(REPAIR_TASK_VIEW_TITLE);
    H2 formTitle = new H2(REPAIR_TASK_VIEW_FORM_TITLE);

    var taskField = new TextField(REPAIR_TASK_LABEL);
    var dueDateTaskField = new DatePicker(REPAIR_TASK_DUEDATE_LABEL);
    var addTaskButton = createAddButton();
    var row = new HorizontalLayout(taskField, dueDateTaskField, addTaskButton);
    row.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
    settingBinder(taskField, dueDateTaskField);

    H2 gridTitle = new H2(REPAIR_TASK_VIEW_GRID_TITLE);
    createGrid();

    add(
         viewTitle,
         createDivider(),
         formTitle,
         row,
         createDivider(),
         gridTitle,
         gridItemsView
    );
  }

  private Button createAddButton() {

    Button button = new Button(REPAIR_ADD_TASK_BUTTON_LABEL);

    button
         .addClickListener(event -> {

           ElevatorRepairTask newTask = new ElevatorRepairTask();

           // binderTask [writeBeanIfValid]:
           // - check Validation of AllFields +
           // - 'write' "Field-Content" para "Entity-Attribute"
           if (binderTask.writeBeanIfValid(newTask)) {
             gridItemsList.add(newTask);
             gridItemsView.setItems(gridItemsList);
           }
         });

    return button;
  }

  private void settingBinder(TextField field, DatePicker datePicker) {

    // BINDER - Style 04: Reference Method
    // Permitido pelo 'new Binder<>(Order.class)'
    // Permitido na AUSENCIA de "Validators"
    binderTask.bind(field, ElevatorRepairTask::getTask, ElevatorRepairTask::setTask);

    // binderTask [forField/bind]: "Link" between "field" -> Entity_Attribute
    // todo:  VALIDATOR - Style 4.0: Multiplos Validators
    // - Multiplos Validators
    binderTask
         .forField(datePicker)
         .withValidator(
              date -> date != null,
              REPAIR_TASK_DUEDATE_REQUIRED_MESSAGE
         )
         .withValidator(
              date -> date.isAfter(LocalDate.now()) || date.isEqual(LocalDate.now()),
              REPAIR_TASK_DUEDATE_VALIDATION_MESSAGE
         )

         // todo:  BINDER - Style 05: Permitido pelo 'new Binder<>(Order.class)'
         .bind(
              elevatorRepairTask -> datePicker.getValue(), // representa o get
              (dateEntity, newDatePicker) -> dateEntity.setDueDate(newDatePicker.toString())
         );
  }

  private void createGrid() {

    gridItemsView.setColumns("task", "dueDate", "completed");
    gridItemsView.getColumnByKey("completed")
                 .setHeader("Status");

    gridItemsView
         .addComponentColumn(columnItem -> {

           var taskCompleted_checkbox = new Checkbox(columnItem.isCompleted());

           taskCompleted_checkbox
                .addValueChangeListener(
                     checkboxState -> {

                       columnItem
                            .setCompleted(checkboxState.getValue());

                       gridItemsView
                            .getDataProvider()
                            .refreshItem(columnItem);
                     });
           return taskCompleted_checkbox;
         })
         .setHeader("Completed");
  }
}