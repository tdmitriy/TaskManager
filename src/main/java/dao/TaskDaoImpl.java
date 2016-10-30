package dao;

import entity.task.PriorityType;
import entity.task.StateType;
import entity.task.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDaoImpl implements ITaskDao {

    private Connection connection;

    public TaskDaoImpl(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public Task getById(final Integer id) throws SQLException {
        if (id == null) return null;

        String query = new StringBuilder()
                .append("SELECT t.id, t.task_name, t.expiration_date, pt.priority_type, st.state_type FROM TASK AS t \n")
                .append("INNER JOIN PRIORITY_TYPE AS pt ON t.priority_id = pt.id \n")
                .append("INNER JOIN STATE_TYPE AS st ON t.state_id = st.id AND t.id = ? LIMIT 1").toString();

        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        Task task = null;

        while (rs.next()) {
            task = getFilledTask(rs);
        }

        st.close();
        rs.close();
        return task;
    }

    @Override
    public List<Task> getAll() throws SQLException {
        List<Task> list = new ArrayList<>();
        String query = new StringBuilder()
                .append("SELECT t.id, t.task_name, t.expiration_date, pt.priority_type, st.state_type FROM TASK AS t \n")
                .append("INNER JOIN PRIORITY_TYPE AS pt ON t.priority_id = pt.id \n")
                .append("INNER JOIN STATE_TYPE AS st ON t.state_id = st.id").toString();

        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            list.add(getFilledTask(rs));
        }

        st.close();
        rs.close();
        return list;
    }

    @Override
    public List<Task> getAllTasksByState(final StateType type) throws SQLException {
        List<Task> list = new ArrayList<>();
        String query = new StringBuilder()
                .append("SELECT t.id, t.task_name, t.expiration_date, pt.priority_type, st.state_type FROM TASK AS t \n")
                .append("INNER JOIN PRIORITY_TYPE AS pt ON t.priority_id = pt.id \n")
                .append("INNER JOIN STATE_TYPE AS st ON t.state_id = st.id \n")
                .append("AND st.state_type LIKE ?").toString();

        PreparedStatement st = connection.prepareStatement(query);
        st.setString(1, type.toString());

        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            list.add(getFilledTask(rs));
        }

        st.close();
        rs.close();
        return list;
    }

    @Override
    public boolean insert(Task entity) throws SQLException {
        if (entity == null) {
            throw new SQLException("Could not insert an entity, the entity is null.");
        }

        String query = new StringBuilder()
                .append("INSERT INTO TASK (task_name, expiration_date, priority_id, state_id)\n")
                .append("SELECT ?, ?, pt.id, st.id FROM PRIORITY_TYPE AS pt, STATE_TYPE AS st \n")
                .append("WHERE pt.priority_type LIKE ? AND st.state_type LIKE ?").toString();

        PreparedStatement st = connection.prepareStatement(query);
        st.setString(1, entity.getName());
        st.setDate(2, entity.getExpirationDate());
        st.setString(3, entity.getPriorityType().toString());
        st.setString(4, entity.getStateType().toString());
        int rows = st.executeUpdate();
        st.close();

        return rows == 1;
    }

    @Override
    public boolean update(Task entity) throws SQLException {
        if (entity == null) {
            throw new SQLException("Could not update an entity, because it is null.");
        }

        String query = new StringBuilder()
                .append("UPDATE TASK AS t \n")
                .append("INNER JOIN PRIORITY_TYPE AS pt ON pt.priority_type LIKE ? \n")
                .append("INNER JOIN STATE_TYPE AS st ON st.state_type LIKE ? \n")
                .append("SET t.task_name=?, t.expiration_date=?, t.priority_id=pt.id, t.state_id=st.id \n")
                .append("WHERE t.id=?").toString();

        PreparedStatement st = connection.prepareStatement(query);
        st.setString(1, entity.getPriorityType().toString());
        st.setString(2, entity.getStateType().toString());
        st.setString(3, entity.getName());
        st.setDate(4, entity.getExpirationDate());
        st.setInt(5, entity.getId());


        int rows = st.executeUpdate();
        st.close();

        return rows == 1;
    }

    @Override
    public boolean delete(final Integer id) throws SQLException {
        if (id != null) {
            String query = "DELETE FROM TASK WHERE id=? LIMIT 1";

            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, id);
            int rows = st.executeUpdate();
            st.close();

            return rows == 1;
        } else throw new SQLException("Could not delete an entity, ID is null.");
    }

    private Task getFilledTask(final ResultSet rs) throws SQLException {
        Task task = new Task();
        task.setId(rs.getInt("id"));
        task.setName(rs.getString("task_name"));
        task.setExpirationDate(new Date(rs.getDate("expiration_date").getTime()));
        task.setPriorityType(PriorityType.valueOf(rs.getString("priority_type").toUpperCase()));
        task.setStateType(StateType.valueOf(rs.getString("state_type").toUpperCase()));
        return task;
    }
}
