package controller;

import helper.CountriesHelper;
import helper.CustomersHelper;
import helper.DivisionsHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;

import javax.xml.transform.Result;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomerRecordsController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TableColumn<Customer, String> addressCol;

    @FXML
    private TableColumn<Customer, String> countryCol;

    @FXML
    private TableColumn<Customer, Integer> customerIDCol;

    @FXML
    private TableView<Customer> customersTableView;

    @FXML
    private TableColumn<Customer, String> divisionCol;

    @FXML
    private TableColumn<Customer, String> nameCol;

    @FXML
    private TableColumn<Customer, String> phoneCol;

    @FXML
    private TableColumn<Customer, String> postalCodeCol;

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

    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddAppointment.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionCreateCustomer(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CreateCustomer.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionDeleteCustomer(ActionEvent event) throws SQLException {
        CustomersHelper.delete(customersTableView.getSelectionModel().getSelectedItem().getCustomerID());
        populateCustomers();
    }

    @FXML
    void onActionUpdateCustomer(ActionEvent event) throws IOException, SQLException {

        if (customersTableView.getSelectionModel().isEmpty()) {
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/UpdateCustomer.fxml"));
        loader.load();

        UpdateCustomerController UCController = loader.getController();
        UCController.sendCustomer(customersTableView.getSelectionModel().getSelectedItem());

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    public void populateCustomers() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        try {

            ResultSet rsCustomers = CustomersHelper.read();
            int customerID = 0;
            String name = null;
            String address = null;
            String postalCode = null;
            String phone = null;
            String country = null;
            String division = null;


            while (rsCustomers.next()) {
                customerID = rsCustomers.getInt("Customer_ID");
                name = rsCustomers.getString("Customer_Name");
                address = rsCustomers.getString("Address");
                postalCode = rsCustomers.getString("Postal_Code");
                phone = rsCustomers.getString("Phone");
                ResultSet rsDivisions = DivisionsHelper.read();
                int countryID = 0;
                while (rsDivisions.next()) {
                    if (rsDivisions.getInt("Division_ID") == rsCustomers.getInt("Division_ID")) {
                        division = rsDivisions.getString("Division");
                        countryID = rsDivisions.getInt("Country_ID");
                    }
                }

                ResultSet rsCountries = CountriesHelper.read();
                while (rsCountries.next()) {
                    if (rsCountries.getInt("Country_ID") == countryID) {
                        country = rsCountries.getString("Country");
                    }
                }



                customerList.add(new Customer(customerID, name, address, postalCode, phone, country, division));
            }
        }
        catch (SQLException e) {
            System.out.println("SQL Exception at customer records.");
        }

        customersTableView.setItems(customerList);

        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateCustomers();
    }
}
