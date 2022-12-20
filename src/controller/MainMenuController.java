package controller;

import functionalInterfaces.UpcomingCheck;
import helper.AppointmentsHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

import static main.Main.infoBox;


public class MainMenuController implements Initializable {

    Stage stage;
    Parent scene;


    @FXML
    void onActionAppointmentSchedule(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/view/AppointmentSchedule.fxml"))));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionCustomerRecords(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CustomerRecords.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionExit(ActionEvent event) {

        System.exit(0);

    }

    @FXML
    void onActionLogout(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Login.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionReports(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Reports.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This lambda checks if there are any upcoming appointments. 
     */
    public static UpcomingCheck UC = () -> {
        try {
            ResultSet rs = AppointmentsHelper.read();
            while (rs.next()) {
                ZonedDateTime start = ZonedDateTime.of(rs.getTimestamp("Start").toLocalDateTime(), ZoneId.of("UTC"));
                ZonedDateTime startLocalZone = start.withZoneSameInstant(ZoneId.systemDefault());
                LocalDateTime startLocalTime = startLocalZone.toLocalDateTime();
                if ((startLocalTime.isEqual(LocalDateTime.now()) || startLocalTime.isAfter(LocalDateTime.now())) &&
                        (startLocalTime.isEqual(LocalDateTime.now().plusMinutes(15)) || startLocalTime.isBefore(LocalDateTime.now().plusMinutes(15)))) {
                    infoBox("You have an upcoming appointment at " + rs.getTimestamp("Start") + ".",
                            "Upcoming Appointment", "Upcoming Appointment");
                    return;
                }
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        infoBox("You have no upcoming appointments.", "No Appointments", "No Appointments");
    };

    public static void upcomingAppointment() throws SQLException {
        UC.checkAppointment();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
