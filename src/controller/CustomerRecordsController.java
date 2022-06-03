package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CustomerRecordsController {

    Stage stage;
    Parent scene;

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
    void onActionUpdateCustomer(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/UpdateCustomer.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

}
