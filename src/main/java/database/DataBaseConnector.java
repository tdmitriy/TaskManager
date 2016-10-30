package database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnector {
    private static final Logger log = LoggerFactory.getLogger(DataBaseConnector.class);
    private static Connection connection = null;

    private DataBaseConnector() {

    }

    private static void openConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            connection = DriverManager.getConnection(PropertiesReader.getConnectionString(),
                    PropertiesReader.getUser(), PropertiesReader.getPassword());
            if (!connection.isClosed())
                log.debug("Connection created.");
        } catch (SQLException e) {
            log.error("Connection failed.\n" + e.getMessage());
            closeConnection();
        } catch (Exception e) {
            log.error("Connection failed.\n" + e.getMessage());
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            openConnection();
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                log.debug("Connection closed.");
            } catch (SQLException e) {
                log.error("Failed to close connection.\n" + e.getMessage());
            }
        } else log.debug("Connection is null, nothing to close.");
    }
}
