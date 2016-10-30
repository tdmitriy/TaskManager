import database.DataBaseConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static BufferedReader bf;
    private static ConsoleHelper helper;


    public static void main(String[] args) throws SQLException, IOException {
        bf = new BufferedReader(new InputStreamReader(System.in));
        Connection connection = DataBaseConnector.getConnection();
        helper = new ConsoleHelper(bf, connection);

        showMainText();
    }

    private static void showMainText() {
        print("=======Simple task manager=======\n");
        print("Choose the number for the action.");

        try {
            while (true) {
                print("\n=================================");
                print("1 - Add new task.");
                print("2 - Show all tasks.");
                print("3 - Show all finished tasks.");
                print("4 - Change task state.");
                print("5 - Exit the program.");
                print("=================================\n");
                print("Enter your choice:");
                String choice = bf.readLine();

                switch (choice) {
                    case "1":
                        print("Add new task.\n");
                        helper.addNewTask();
                        break;
                    case "2":
                        print("Showing all tasks..\n");
                        helper.showAllTasks(false);
                        break;
                    case "3":
                        print("Showing all finished tasks..\n");
                        helper.showAllTasks(true);
                        break;
                    case "4":
                        print("Changing task state..\n");
                        helper.changeTaskState();
                        break;
                    case "5":
                        exit();
                        break;
                    default:
                        print("Wrong choice.");
                        break;
                }
            }
        } catch (IOException ex) {
            log.error("Exception while reading user input.\n" + ex.getMessage());
        }
    }

    private static void exit() {
        print("Exiting the program..");
        DataBaseConnector.closeConnection();
        System.exit(0);
    }


    private static void print(String msg) {
        System.out.println(msg);
    }
}
