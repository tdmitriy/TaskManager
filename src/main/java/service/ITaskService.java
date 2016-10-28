package service;

import entity.task.StateType;
import entity.task.Task;

import java.sql.SQLException;
import java.util.List;

public interface ITaskService {
    Task getById(final Integer id);

    List<Task> getAll() throws SQLException;

    List<Task> getAllTasksByState(final StateType type);

    boolean insert(final Task entity);

    boolean update(final Task entity);

    boolean delete(final Integer id);
}
