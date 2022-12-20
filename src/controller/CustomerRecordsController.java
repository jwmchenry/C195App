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
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This class is responsible for displaying the customers on record.
 */
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

    /**
     * This method exits the application.
     * @param event
     */
    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    /**
     * This method returns to the previous menu.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionMenuBack(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * This method takes the user to the form to add an appointment.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddAppointment.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * This method takes the user to the form to create a customer.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionCreateCustomer(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CreateCustomer.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }


    /**
     * This method deletes the currently selected customer on the customers table view.
     * @param event
     * @throws SQLException
     */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) throws SQLException {
        CustomersHelper.delete(customersTableView.getSelectionModel().getSelectedItem().getCustomerID());
        populateCustomers();
    }

    /**
     * This method sends the user to the update customer form with the customer information populated automatically.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
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

    /**
     * This method populates the customers on the table view.
     */
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

    /**
     * This method calls the method that populates the customers when the form is initialized.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateCustomers();
    }
}
