import database.DataBaseConnector;
import entity.task.StateType;
import entity.task.Task;
import service.ITaskService;
import service.TaskServiceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = DataBaseConnector.getConnection();

        ITaskService taskService = new TaskServiceImpl(connection);

        List<Task> tasks = taskService.getAllTasksByState(StateType.EXPIRED);
        for (Task task : tasks) {
            System.out.println(task);
        }


        DataBaseConnector.closeConnection();
    }
}
