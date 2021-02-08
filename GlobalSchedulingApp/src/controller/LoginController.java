/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utilities.DataHandler;

/**
 * FXML Controller class
 *
 * @author James Spencer
 */
public class LoginController implements Initializable {
    Stage stage;
    Parent scene;
    
    @FXML private Label titleLabel;
    @FXML private Label loginLabel;
    @FXML private Label usernameLabel;
    @FXML private Label passwordLabel;
    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;
    @FXML private Label currentLocationLabel;
    @FXML private Label locationLabel;
    @FXML private Button loginBtn;
    @FXML private Button exitBtn;
    
    @FXML
    void onExitBtnClicked(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onLoginBtnClicked(ActionEvent event) throws IOException {
        String usernameInput = usernameTextField.getText();
        String passwordInput = passwordTextField.getText();
        if (!usernameInput.equals("test") || !passwordInput.equals("test")) {
            showWrongInfoBox();
        }
        else {
            DataHandler.currentUser = usernameInput;
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setTitle("Main Menu");
            scene = FXMLLoader.load(getClass().getResource("/view/MainWindowView.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            stage.centerOnScreen();
        }
    }
    
    private void showWrongInfoBox (){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Incorrect Username Or Password");
        alert.setHeaderText("The username or password you used was incorrect.");
        alert.showAndWait();
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Locale locale = Locale.getDefault();
        if (locale.toString().contains("en")) {
            // SET LANGAUGE PACK HERE AND ALL LABELS HERE
            // BELOW IS JUST A TEST
            currentLocationLabel.setText("United States");
        }
        if (locale.toString().contains("fr")) {
            // SET LANGUAGE PACK AND ALL LABELS HERE
            // BELOW IS JUST A TEST
            currentLocationLabel.setText("France");
        }
    }    
}
