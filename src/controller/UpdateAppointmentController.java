package controller;

import helper.AppointmentsHelper;
import helper.ContactsHelper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;



public class UpdateAppointmentController {

    Stage stage;
    Parent scene;

    @FXML
    private TextField apptIDTxt;

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


    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AppointmentSchedule.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException, SQLException {

        int apptID = Integer.parseInt(apptIDTxt.getText());
        String title = titleTxt.getText();
        String description = descTxt.getText();
        String location = locationTxt.getText();
        String type = typeTxt.getText();
        LocalDateTime start = LocalDateTime.parse(startTxt.getText());
        LocalDateTime end = LocalDateTime.parse(endTxt.getText());
        int customerID = Integer.parseInt(customerIDTxt.getText());
        int userID = Integer.parseInt(userIDTxt.getText());
        int contactID = contactCmbBx.getValue().getContactID();

        AppointmentsHelper.update(apptID, title, description, location, type, start, end, customerID, userID, contactID);

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AppointmentSchedule.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    public void sendAppointment(Appointment appointment) throws SQLException {

        ObservableList<Contact> contacts = ContactsHelper.contactList();
        contactCmbBx.setItems(contacts);

        apptIDTxt.setText(String.valueOf(appointment.getApptID()));
        titleTxt.setText(appointment.getTitle());
        locationTxt.setText(appointment.getLocation());
        contactCmbBx.getSelectionModel().select(appointment.getContact());
        typeTxt.setText(appointment.getType());
        startTxt.setText(appointment.getStart().toString());
        endTxt.setText(appointment.getEnd().toString());
        descTxt.setText(appointment.getDescription());
        customerIDTxt.setText(String.valueOf(appointment.getCustomerID()));
        userIDTxt.setText(String.valueOf(appointment.getUserID()));
    }
}
