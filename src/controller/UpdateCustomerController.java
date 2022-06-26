package controller;

import helper.CountriesHelper;
import helper.CustomersHelper;
import helper.DivisionsHelper;
import helper.JoinHelper;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class UpdateCustomerController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField addressTxt;

    @FXML
    private ComboBox<String> countryCmbBx;

    @FXML
    private ComboBox<String> divisionCmbBx;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField phoneTxt;

    @FXML
    private TextField postalCodeTxt;


    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CustomerRecords.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException, SQLException {
        int customerID = Integer.parseInt(idTxt.getText());
        String name = nameTxt.getText();
        String address = addressTxt.getText();
        String postalCode = postalCodeTxt.getText();
        String phoneNumber = phoneTxt.getText();

        int divisionID = 0;
        ResultSet rs = DivisionsHelper.read();

        while (rs.next()) {
            if (divisionCmbBx.getValue().equals(rs.getString("Division"))) {
                divisionID = rs.getInt("Division_ID");
            }
        }

        CustomersHelper.update(name, address, postalCode, phoneNumber, divisionID, customerID);

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CustomerRecords.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onActionSelectCountry(ActionEvent actionEvent) throws SQLException {
        ObservableList<String> divisionList = FXCollections.observableArrayList();
        String countryName = countryCmbBx.getValue();
        ResultSet rs = JoinHelper.divisionsCountriesRead();

        while (rs.next()) {
            if (countryName.equals(rs.getString("Country"))) {
                divisionList.add(rs.getString("Division"));
            }
        }
        divisionCmbBx.setItems(divisionList);
    }

    public void sendCustomer(Customer customer) throws SQLException {


        ObservableList<String> countryList = FXCollections.observableArrayList();
        ResultSet rsCountriesList = CountriesHelper.read();
        String countryName;
        while (rsCountriesList.next()) {
            countryName = rsCountriesList.getString("Country");
            countryList.add(countryName);
        }

        countryCmbBx.setItems(countryList);

        ResultSet rs = JoinHelper.customersDivisionCountryRead();
        while (rs.next()) {
            if (rs.getInt("Customer_ID") == customer.getCustomerID()) {
                idTxt.setText(String.valueOf(customer.getCustomerID()));
                nameTxt.setText(rs.getString("Customer_Name"));
                addressTxt.setText(rs.getString("Address"));
                postalCodeTxt.setText(rs.getString("Postal_Code"));
                phoneTxt.setText(rs.getString("Phone"));

                countryCmbBx.getSelectionModel().select(rs.getString("Country"));
                divisionCmbBx.getSelectionModel().select(rs.getString("Division"));

                ObservableList<String> divisionList = FXCollections.observableArrayList();
                ResultSet rsDivisions = DivisionsHelper.read();
                String divisionName;
                while (rsDivisions.next()) {
                    divisionName = rsDivisions.getString("Division");
                    divisionList.add(divisionName);
                }

                divisionCmbBx.setItems(divisionList);
            }
        }
    }
}
