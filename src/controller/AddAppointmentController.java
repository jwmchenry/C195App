package controller;

import helper.AppointmentsHelper;
import helper.ContactsHelper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;
import model.Contact;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;
import static main.Main.infoBox;

/**
 * This class provides the controlling methods for the GUI controls of the appointment adding interface.
 */
public class AddAppointmentController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private ComboBox<Contact> contactCmbBx;

    @FXML
    private TextField customerIDTxt;

    @FXML
    private TextArea descTxt;

    @FXML
    private TextField endTxt;

    @FXML
    private TextField locationTxt;

    @FXML
    private TextField startTxt;

    @FXML
    private TextField titleTxt;

    @FXML
    private TextField typeTxt;

    @FXML
    private TextField userIDTxt;

    /**
     * This method does not save the appointment and returns to the main menu.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CustomerRecords.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * This method takes the information input and creates an appointment to be stored in the database.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException, SQLException {

        int apptID = Main.newApptID();
        String title = titleTxt.getText();
        String description = descTxt.getText();
        String location = locationTxt.getText();
        String type = typeTxt.getText();

        String startText = startTxt.getText();
        DateTimeFormatter startFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        DateTimeFormatter startTimeOnlyFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String endText = endTxt.getText();
        DateTimeFormatter endFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        DateTimeFormatter endTimeOnlyFormatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalDateTime start = LocalDateTime.parse(startText, startFormatter);
        LocalDateTime end = LocalDateTime.parse(endText, endFormatter);

        String startTimeOnlyString = start.format(startTimeOnlyFormatter);
        LocalTime startTimeOnly = LocalTime.parse(startTimeOnlyString, startTimeOnlyFormatter);

        String endTimeOnlyString = end.format(endTimeOnlyFormatter);
        LocalTime endTimeOnly = LocalTime.parse(endTimeOnlyString, endTimeOnlyFormatter);

        LocalTime businessHoursOpen = LocalTime.parse("08:00");
        LocalTime businessHoursClose = LocalTime.parse("22:00");

        if (startTimeOnly.isBefore(businessHoursOpen)) {
            infoBox("The entered time is before business hours, please choose a different time.",
                    "Incorrect Scheduling", "Incorrect Scheduling");
            return;
        } else if(endTimeOnly.isAfter(businessHoursClose)) {
            infoBox("The entered time is after business hours, please choose a different time.",
                    "Incorrect Scheduling", "Incorrect Scheduling");
            return;
        }
        ZonedDateTime zonedStart = ZonedDateTime.of(start, ZoneId.of("US/Eastern"));
        ZonedDateTime startOverlap = zonedStart.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime startOverlapLDT = startOverlap.toLocalDateTime();

        ZonedDateTime zonedEnd = ZonedDateTime.of(end, ZoneId.of("US/Eastern"));
        ZonedDateTime endOverlap = zonedEnd.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime endOverlapLDT = endOverlap.toLocalDateTime();

        ResultSet rs = AppointmentsHelper.read();
        while (rs.next()) {
            if ((startOverlapLDT.isAfter(rs.getTimestamp("Start").toLocalDateTime()) || startOverlapLDT.isEqual(rs.getTimestamp("Start").toLocalDateTime()))
                    && (endOverlapLDT.isBefore(rs.getTimestamp("End").toLocalDateTime()) || endOverlapLDT.isEqual(rs.getTimestamp("End").toLocalDateTime())))
            {
                infoBox("This appointment time overlaps with an existing appointment. Please choose a different time.",
                        "Overlapping Appointments", "Overlapping Appointments");
                return;
            }

        }

        int customerID = Integer.parseInt(customerIDTxt.getText());
        int userID = Integer.parseInt(userIDTxt.getText());
        int contactID = contactCmbBx.getValue().getContactID();

        AppointmentsHelper.create(apptID, title, description, location, type, ZonedDateTime.of(start, ZoneId.of("US/Eastern")), ZonedDateTime.of(end, ZoneId.of("US/Eastern")), customerID, userID, contactID);

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AppointmentSchedule.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }


    /**
     * this method is run when the window is initialized and populates the contact combo box.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Contact> contactsList = ContactsHelper.contactList();
            contactCmbBx.setItems(contactsList);
        }
        catch (SQLException e) {
            System.out.println("SQL error initializing add appointments controller.");
        }
    }
}
