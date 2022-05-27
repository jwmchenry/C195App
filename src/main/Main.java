package main;

import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Login.fxml")));
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(new Scene(root,450,400));
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
        JDBC.openConnection();
        JDBC.closeConnection();

    }
}
