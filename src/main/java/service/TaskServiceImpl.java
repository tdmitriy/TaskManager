package service;

import dao.ITaskDao;
import dao.TaskDaoImpl;
import entity.task.StateType;
import entity.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TaskServiceImpl implements ITaskService {
    private static final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    // We can replace this with Spring DI in future.
    private ITaskDao taskDao;

    private Connection connection;

    public TaskServiceImpl(final Connection connection) {
        if (connection == null) {
            log.error("Connection is null.");
            throw new NullPointerException("Connection is null.");
        }

        this.connection = connection;
        taskDao = new TaskDaoImpl(this.connection);
    }

    @Override
    public Task getById(Integer id) {
        try {
            connection.setAutoCommit(false);
            Task task = taskDao.getById(id);
            connection.commit();
            return task;
        } catch (SQLException ex) {
            rollbackTransaction();
            log.error("Transaction is being rolled back on method getById().\n" + ex.getMessage());
            return null;
        } finally {
            restoreAutoCommitToDefault();
        }
    }

    @Override
    public List<Task> getAll() throws SQLException {
        try {
            connection.setAutoCommit(false);
            List<Task> tasks = taskDao.getAll();
            connection.commit();
            return tasks;
        } catch (SQLException ex) {
            rollbackTransaction();
            log.error("Transaction is being rolled back on method getAll().\n" + ex.getMessage());
            return null;
        } finally {
            restoreAutoCommitToDefault();
        }
    }

    @Override
    public List<Task> getAllTasksByState(StateType type) {
        try {
            connection.setAutoCommit(false);
            List<Task> tasks = taskDao.getAllTasksByState(type);
            connection.commit();
            return tasks;
        } catch (SQLException ex) {
            rollbackTransaction();
            log.error("Transaction is being rolled back on method getAllTasksByState().\n" + ex.getMessage());
            return null;
        } finally {
            restoreAutoCommitToDefault();
        }
    }

    @Override
    public boolean insert(Task entity) {
        try {
            connection.setAutoCommit(false);
            boolean result = taskDao.insert(entity);
            connection.commit();
            return result;
        } catch (SQLException ex) {
            rollbackTransaction();
            log.error("Transaction is being rolled back on method insert().\n" + ex.getMessage());
            return false;
        } finally {
            restoreAutoCommitToDefault();
        }
    }

    @Override
    public boolean update(Task entity) {
        try {
            connection.setAutoCommit(false);
            boolean result = taskDao.update(entity);
            connection.commit();
            return result;
        } catch (SQLException ex) {
            rollbackTransaction();
            log.error("Transaction is being rolled back on method update().\n" + ex.getMessage());
            return false;
        } finally {
            restoreAutoCommitToDefault();
        }
    }

    @Override
    public boolean delete(Integer id) {
        try {
            connection.setAutoCommit(false);
            boolean result = taskDao.delete(id);
            connection.commit();
            return result;
        } catch (SQLException ex) {
            rollbackTransaction();
            log.error("Transaction is being rolled back on method delete().\n" + ex.getMessage());
            return false;
        } finally {
            restoreAutoCommitToDefault();
        }
    }

    private void rollbackTransaction() {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            log.error("Error: cannot rollbackTransaction transaction.\n" + ex.getMessage());
        }
    }

    private void restoreAutoCommitToDefault() {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            log.error("Error: cannot setAutoCommit(true).\n" + e.getMessage());
        }
    }
}
