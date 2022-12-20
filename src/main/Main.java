package main;

import helper.AppointmentsHelper;
import helper.CustomersHelper;
import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;


/**
 * This class is the entry point into the application.
 */
public class Main extends Application {

    public static int customerIDCounter = 1;
    public static int apptIDCounter = 1;

    /**
     * This method is a counter that will give a customer a unique ID based on positive increments.
     * @return
     * @throws SQLException
     */
    public static int newCustomerID() throws SQLException {

        ResultSet rs = CustomersHelper.read();

        while (rs.next()) {

           customerIDCounter = rs.getInt("Customer_ID");

        }

        customerIDCounter++;

        return customerIDCounter;

    }

    /**
     * This method is a counter that will give an appointment a unique ID based on positive increments.
     * @return
     * @throws SQLException
     */
    public static int newApptID() throws SQLException {

        ResultSet rs = AppointmentsHelper.read();

        while (rs.next()) {

            apptIDCounter = rs.getInt("Appointment_ID");

        }

        apptIDCounter++;

        return apptIDCounter;
    }

    /**
     * This is a method that will create an alert with the given arguments.
     * @param infoMessage
     * @param titleBar
     * @param headerMessage
     */
    public static void infoBox(String infoMessage, String titleBar, String headerMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

    /**
     * This method begins the application.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Login.fxml")));
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(new Scene(root,450,400));
        primaryStage.show();
    }

    /**
     * This method is the starting point of the application and deals with database connectivity.
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();

    }
}
