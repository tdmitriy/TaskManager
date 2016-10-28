package dao;

import entity.task.StateType;
import entity.task.Task;

import java.sql.SQLException;
import java.util.List;

public interface ITaskDao {
    Task getById(final Integer id) throws SQLException;

    List<Task> getAll() throws SQLException;

    List<Task> getAllTasksByState(final StateType type) throws SQLException;

    boolean insert(final Task entity) throws SQLException;

    boolean update(final Task entity) throws SQLException;

    boolean delete(final Integer id) throws SQLException;
}
