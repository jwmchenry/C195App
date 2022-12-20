package controller;

import functionalInterfaces.TextSetter;
import helper.UsersHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import static controller.MainMenuController.UC;
import static java.time.ZoneId.systemDefault;

/**
 * This class is responsible for the user logging into the application.
 */
public class LoginController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Label errorLbl;

    @FXML
    private Button exitBtn;

    @FXML
    private Label languageLbl;

    @FXML
    private Label languageTextLbl;

    @FXML
    private Label locationLbl;

    @FXML
    private Label locationTextLbl;

    @FXML
    private Button loginBtn;

    @FXML
    private Label passwordLbl;

    @FXML
    private TextField passwordTxt;

    @FXML
    private Label titleLbl;

    @FXML
    private Label usernameLbl;

    @FXML
    private TextField usernameTxt;

    /**
     * This method exits the application.
     * @param event
     */
    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    /**
     * This method is responsible for recording login activity and checking login credentials. Additionally, this is
     * where the lambda is called from the MainMenuController to check if there are any upcoming appointments.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onActionLogin(ActionEvent event) throws IOException, SQLException {

        ResultSet rs = UsersHelper.read();

        FileWriter fw = new FileWriter("login_activity.txt", true);
        PrintWriter pw = new PrintWriter(fw);

        pw.println("Username: " + usernameTxt.getText());
        pw.println("Password: " + passwordTxt.getText());
        pw.println("Time: " + LocalDateTime.now());

        boolean loginAttempt = false;

        while (rs.next()) {
            if (rs.getString("User_Name").equals(usernameTxt.getText())) {
                if (rs.getString("Password").equals(passwordTxt.getText())) {
                    loginAttempt = true;
                }
            }
        }
        if (loginAttempt) {
            pw.println("Successful login");
            pw.println("");
            pw.close();
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
            UC.checkAppointment();
        } else {
            pw.println("Unsuccessful login");
            errorLbl.setText(TS.setText("Username or password was incorrect") + ".");
            pw.println("");
            pw.close();
        }
    }

    /**
     * This lambda translates the key to French if needed.
     */
    public TextSetter TS = t -> {
        String translatedText = "";
        ResourceBundle rb = ResourceBundle.getBundle("utilities/Lang", Locale.forLanguageTag("fr"));

        if (Locale.getDefault().getLanguage().equals("fr")) {
            translatedText = rb.getString(t);
            return translatedText;
        }

        return t;
    };

    /**
     * This method is responsible for setting the language of the login page using the system default language,
     * and to do this a lambda has been created to handle the translations if needed.
     * @param url
     * @param resourceBundle
     */
    public void initialize (URL url, ResourceBundle resourceBundle) {

        locationLbl.setText(String.valueOf(systemDefault()));

        titleLbl.setText(TS.setText("Customer Scheduling System"));
        usernameLbl.setText(TS.setText("username"));
        passwordLbl.setText(TS.setText("password"));
        locationTextLbl.setText(TS.setText("location") + ": ");
        languageTextLbl.setText(TS.setText("language") + ": ");
        languageLbl.setText(TS.setText("English"));
        loginBtn.setText(TS.setText("Login"));
        exitBtn.setText(TS.setText("Exit"));

    }

}
