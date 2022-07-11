package controller;

import helper.AppointmentsHelper;
import helper.ContactsHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

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

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CustomerRecords.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException, SQLException {

        int apptID = Main.newApptID();
        String title = titleTxt.getText();
        String description = descTxt.getText();
        String location = locationTxt.getText();
        String type = typeTxt.getText();
        LocalDateTime start = LocalDateTime.parse(startTxt.getText());
        LocalDateTime end = LocalDateTime.parse(endTxt.getText());
        int customerID = Integer.parseInt(customerIDTxt.getText());
        int userID = Integer.parseInt(userIDTxt.getText());
        int contactID = contactCmbBx.getValue().getContactID();

        AppointmentsHelper.create(apptID, title, description, location, type, start, end, customerID, userID, contactID);


        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AppointmentSchedule.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            ObservableList<Contact> contactsList = ContactsHelper.contactList();
            contactCmbBx.setItems(contactsList);

            startTxt.setText(String.valueOf(LocalDateTime.now()));
        }
        catch (SQLException e) {
            System.out.println("SQL error initializing add appointments controller.");
        }
    }
}
