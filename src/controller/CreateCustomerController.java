package controller;

import helper.CustomersHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class CreateCustomerController {

    Stage stage;
    Parent scene;

    @FXML
    private TextField addressTxt;

    @FXML
    private ComboBox<?> countryCmbBx;

    @FXML
    private ComboBox<?> divisionCmbBx;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField phoneTxt;

    @FXML
    private TextField postalCodeTxt;

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CustomerRecords.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException, SQLException {

        int customerID = Main.newCustomerID();
        String name = nameTxt.getText();
        String address = addressTxt.getText();
        String postalCode = postalCodeTxt.getText();
        String phoneNumber = phoneTxt.getText();
//        int divisionID = (int) divisionCmbBx.getSelectionModel().getSelectedItem();

        CustomersHelper.create(customerID, name, address, postalCode, phoneNumber, 4);

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CustomerRecords.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

}
