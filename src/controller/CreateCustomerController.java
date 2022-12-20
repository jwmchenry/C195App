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
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This class is responsible for creating a customer in the database.
 */
public class CreateCustomerController implements Initializable {

    Stage stage;
    Parent scene;


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

    /**
     * This method cancels the creation and returns to the previous menu.
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
     * This method saves the creation of the customer and returns to the previous menu.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException, SQLException {

        int customerID = Main.newCustomerID();
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

        CustomersHelper.create(customerID, name, address, postalCode, phoneNumber, divisionID);

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CustomerRecords.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * This method populates the division combo box with the first level divisions associated with the country selected.
     * @param event
     * @throws SQLException
     */
    @FXML
    void onActionSelectCountry(ActionEvent event) throws SQLException {

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

    /**
     * This method is run when the window is initialized and here the country combo box is populated.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> countryList = FXCollections.observableArrayList();
        try {
            ResultSet rs = CountriesHelper.read();

            String countryName;
            while (rs.next()) {
                countryName = rs.getString("Country");
                countryList.add(countryName);
            }
        }
        catch (SQLException e) {
            System.out.println("SQL Error.");
        }

        countryCmbBx.setItems(countryList);



    }

}
