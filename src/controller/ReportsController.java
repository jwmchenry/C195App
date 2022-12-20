package controller;

import helper.AppointmentsHelper;
import helper.ContactsHelper;
import helper.CustomersHelper;
import helper.JoinHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    @FXML
    private ComboBox<String> apptMonthCmbBx;

    @FXML
    private ComboBox<String> apptTypeCmbBx;

    @FXML
    private ComboBox<String> contactCmbBx;

    @FXML
    private ComboBox<String> locationCmbBx;

    @FXML
    private TableView<Appointment> contactTableView;

    @FXML
    private TableColumn<Appointment, Integer> customerIDCol;

    @FXML
    private TableColumn<Appointment, String> descCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> endCol;

    @FXML
    private TableColumn<Appointment, Integer> apptIDCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private Label resultTxt;

    @FXML
    private Label domesticResultTxt;

    @FXML
    private Label intlResultTxt;

    @FXML
    private Label locationResultTxt;

    private int counter;

    Stage stage;
    Parent scene;

    @FXML
    void onActionSelectMonth(ActionEvent event) throws SQLException {
        ResultSet rs = AppointmentsHelper.read();

        String type = apptTypeCmbBx.getSelectionModel().getSelectedItem();
        String month = apptMonthCmbBx.getSelectionModel().getSelectedItem();



        while (rs.next()) {
            if (rs.getString("Type").equals(type)) {
                String monthConvert = null;
                if (rs.getTimestamp("Start").toLocalDateTime().getMonth().getDisplayName(TextStyle.FULL, Locale.US).equals(month)) {
                    counter++;
                }
            }
        }

        resultTxt.setText(String.valueOf(counter));
        counter = 0;
    }

    @FXML
    void onActionSelectType(ActionEvent event) throws SQLException {
        ResultSet rs = AppointmentsHelper.read();

        String type = apptTypeCmbBx.getSelectionModel().getSelectedItem();
        String month = apptMonthCmbBx.getSelectionModel().getSelectedItem();



        while (rs.next()) {
            if (rs.getString("Type").equals(type)) {
                String monthConvert = null;
                if (rs.getTimestamp("Start").toLocalDateTime().getMonth().getDisplayName(TextStyle.FULL, Locale.US).equals(month)) {
                    counter++;
                }
            }
        }

        resultTxt.setText(String.valueOf(counter));
        counter = 0;
    }

    @FXML
    void onActionSelectContact(ActionEvent event) throws SQLException {
        ResultSet rs = AppointmentsHelper.read();
        ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();

        while (rs.next()) {
            int apptID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");

            LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
            DateTimeFormatter startFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
            String start = startDateTime.format(startFormatter);
            LocalDateTime parsedStart = LocalDateTime.parse(start, startFormatter);
            ZonedDateTime zonedStartUTC = ZonedDateTime.of(parsedStart, ZoneId.of("UTC"));
            ZonedDateTime zonedStartUser = zonedStartUTC.withZoneSameInstant(ZoneId.systemDefault());
            LocalDateTime zonedLocalStart = zonedStartUser.toLocalDateTime();
            start = zonedLocalStart.format(startFormatter);

            LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();
            DateTimeFormatter endFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
            String end = endDateTime.format(endFormatter);
            LocalDateTime parsedEnd = LocalDateTime.parse(end, endFormatter);
            ZonedDateTime zonedEndUTC = ZonedDateTime.of(parsedEnd, ZoneId.of("UTC"));
            ZonedDateTime zonedEndUser = zonedEndUTC.withZoneSameInstant(ZoneId.systemDefault());
            LocalDateTime zonedLocalEnd = zonedEndUser.toLocalDateTime();
            end = zonedLocalEnd.format(startFormatter);

            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");

            String contactName = null;
            ResultSet rsContacts = ContactsHelper.read();
            while (rsContacts.next()) {
                if (contactID == rsContacts.getInt("Contact_ID")) {
                    contactName = rsContacts.getString("Contact_Name");
                }
            }

            Contact contact = new Contact(contactID, contactName);

            Appointment appointment = new Appointment(apptID, title, description, location, type, start, end, customerID,
                    userID, contact);

            if (contactName.equals(contactCmbBx.getSelectionModel().getSelectedItem())) {
                appointmentsList.add(appointment);
            }

        }

        contactTableView.setItems(appointmentsList);

        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

    }

    @FXML
    void onActionSelectLocation(ActionEvent event) throws SQLException {

    String location = locationCmbBx.getSelectionModel().getSelectedItem();
    int counter = 0;
    ResultSet rs = AppointmentsHelper.read();

    while (rs.next()) {
        if (location.equals(rs.getString("Location"))) {
            counter++;
        }
    }

    locationResultTxt.setText(String.valueOf(counter));


    }

    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onActionMenuBack(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<String> typeList = FXCollections.observableArrayList();
            ObservableList<String> monthList = FXCollections.observableArrayList();
            ObservableList<String> contactList = FXCollections.observableArrayList();
            ObservableList<String> locationList = FXCollections.observableArrayList();

            monthList.addAll("January", "February", "March", "April", "May", "June", "July", "August", "September",
                    "October", "November", "December");

            ResultSet rs = AppointmentsHelper.read();
            while (rs.next()) {
                String type = rs.getString("Type");
                int contactID = rs.getInt("Contact_ID");
                String location = rs.getString("Location");

                if (!typeList.contains(type)) {
                    typeList.add(type);
                }

                if (!locationList.contains(location)) {
                    locationList.add(location);
                }

                String contactName = null;
                ResultSet rsContacts = ContactsHelper.read();
                while (rsContacts.next()) {
                    if (contactID == rsContacts.getInt("Contact_ID")) {
                        contactName = rsContacts.getString("Contact_Name");
                    }
                }
                if (!contactList.contains(contactName)) {
                    contactList.add(contactName);
                }
            }

            apptTypeCmbBx.setItems(typeList);
            apptMonthCmbBx.setItems(monthList);
            contactCmbBx.setItems(contactList);
            locationCmbBx.setItems(locationList);

            rs = CustomersHelper.read();
            int intlCustomerCount = 0;
            int domesticCustomerCount = 0;

            while (rs.next()) {
                if (rs.getInt("Division_ID") <= 54) {
                    domesticCustomerCount++;
                } else if (rs.getInt("Division_ID") >54){
                    intlCustomerCount++;
                }
            }

            domesticResultTxt.setText(String.valueOf(domesticCustomerCount));
            intlResultTxt.setText(String.valueOf(intlCustomerCount));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
