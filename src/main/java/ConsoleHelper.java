import entity.task.PriorityType;
import entity.task.StateType;
import entity.task.Task;
import service.ITaskService;
import service.TaskServiceImpl;
import utils.JdbcUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class ConsoleHelper {

    private BufferedReader bf;
    private ITaskService taskService;

    ConsoleHelper(BufferedReader bf, Connection connection) {
        this.bf = bf;
        taskService = new TaskServiceImpl(connection);
    }

    public void changeTaskState() throws IOException {
        while (true) {
            print("Enter ID of the task to change the current state:");
            try {
                Integer id = Integer.valueOf(bf.readLine());
                Task taskForEdit = taskService.getById(id);

                if (taskForEdit == null) {
                    print(String.format("The task with given id=%d not found.", id));
                    break;
                }

                print("Editing task is:");
                print(taskForEdit.toString());

                StateType stateType = parseStateType();
                taskForEdit.setStateType(stateType);

                boolean updated = taskService.update(taskForEdit);

                if (updated) print("The task has been successfully updated.\n");
                else print("Could not update a task. See console error log for details.\n");

                break;

            } catch (NumberFormatException ex) {
                print("Wrong input.");
            }
        }
    }

    public void addNewTask() throws IOException {
        print("Enter a task name:");

        String taskName = bf.readLine();
        Date dateOfExpiration = parseDate();
        PriorityType priorityType = parsePriorityType();

        Task newTask = new Task();
        newTask.setName(taskName);
        newTask.setExpirationDate(dateOfExpiration);
        newTask.setPriorityType(priorityType);

        boolean inserted = taskService.insert(newTask);

        if (inserted) print("A new task has been successfully added.\n");
        else print("Could not add a new task. See console error log for details.\n");
    }

    public void showAllTasks(boolean showFinishedTasks) {
        List<Task> tasks =
                showFinishedTasks ? taskService.getAllTasksByState(StateType.FINISHED) : taskService.getAll();

        if (tasks.isEmpty()) print("There is no tasks to show. Tasks list is empty.");

        for (Task task : tasks) {
            print(task.toString());
        }
    }

    public Date parseDate() throws IOException {
        while (true) {
            print("Enter expiration date in format (`yyyy-MM-dd` for example 2016-01-20):");
            String date = bf.readLine();
            Date dateOfExpiration = JdbcUtils.parseDate(date);

            if (dateOfExpiration == null)
                print("You entered a wrong type.");
            else return dateOfExpiration;
        }
    }

    public PriorityType parsePriorityType() throws IOException {
        while (true) {
            try {
                print("Enter priority of the task (Low, Medium, High):");
                return PriorityType.valueOf(bf.readLine().toUpperCase());
            } catch (IllegalArgumentException ex) {
                print("You entered a wrong type.");
            }
        }
    }

    public StateType parseStateType() throws IOException {
        while (true) {
            try {
                print("Enter the state of the task (New, Expired, Finished):");
                return StateType.valueOf(bf.readLine().toUpperCase());
            } catch (IllegalArgumentException ex) {
                print("You entered a wrong type.");
            }
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}
