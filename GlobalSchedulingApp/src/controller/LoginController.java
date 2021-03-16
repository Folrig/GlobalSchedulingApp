/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.*;
import java.net.URL;
import java.time.*;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import model.Customer;
import model.User;
import utilities.DataHandler;

/**
 * FXML Controller class
 *
 * @author James Spencer
 */
public class LoginController implements Initializable {
    Stage stage;
    Parent scene;
    FileWriter fWriter;
    PrintWriter pWriter;
    String fileName = "login_activity.txt";
    
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
    @FXML private Label zoneLabel;
    @FXML private Label currentZoneIdLabel;
    
    @FXML
    void onExitBtnClicked(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onLoginBtnClicked(ActionEvent event) throws IOException {
        fWriter = new FileWriter(fileName, true);
        pWriter = new PrintWriter(fWriter);
        String usernameInput = usernameTextField.getText();
        String passwordInput = passwordTextField.getText();
        if (!usernameInput.equals("test") || !passwordInput.equals("test")) {
            pWriter.println("** LOGIN ATTEMPT **   Result: INVALID  Date: " + LocalDate.now(ZoneOffset.UTC) +
                    "  Time: " + LocalTime.now(ZoneOffset.UTC) + "  Username: " + usernameInput + 
                    "  Password: " + passwordInput);
            pWriter.close();
            showWrongInfoBox();
        }
        else {
            for (User user : (ObservableList<User>)DataHandler.readUsers()) {
                if (usernameInput.equals(user.getName())) {
                    DataHandler.currentUser = user;
                    break;
                }
            }
            pWriter.println("** LOGIN ATTEMPT **   Result: VALID  Date: " + LocalDate.now(ZoneOffset.UTC) +
                    "  Time: " + LocalTime.now(ZoneOffset.UTC) + "  Username: " + usernameInput + 
                    "  Password: " + passwordInput);
            pWriter.close();
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            if (Locale.getDefault().getLanguage().equals("fr")){
                stage.setTitle(DataHandler.rb.getString("menu") + " " + DataHandler.rb.getString("main"));
            } else {
                stage.setTitle("Main Menu");
            }
            scene = FXMLLoader.load(getClass().getResource("/view/MainWindowView.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            stage.centerOnScreen();
        }
    }
    
    private void showWrongInfoBox (){
        Alert alert = new Alert(AlertType.WARNING);
        if (Locale.getDefault().getLanguage().equals("fr")){
            alert.setTitle(DataHandler.rb.getString("incorrect") + " " + DataHandler.rb.getString("username") +
                    " " + DataHandler.rb.getString("or") + " " + DataHandler.rb.getString("password"));
            alert.setHeaderText(DataHandler.rb.getString("the") + " " + DataHandler.rb.getString("username") +
                    " " + DataHandler.rb.getString("or") + " " + DataHandler.rb.getString("password") +
                    " " + DataHandler.rb.getString("is") + " " + DataHandler.rb.getString("incorrect"));
        } else {
            alert.setTitle("Incorrect Username Or Password");
            alert.setHeaderText("The username or password is incorrect.");
        }
        alert.showAndWait();
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DataHandler.currentZoneId = ZoneId.systemDefault();
        Locale locale = Locale.getDefault();
        if (locale.toString().contains("en")) {
            currentLocationLabel.setText("United States");
            currentZoneIdLabel.setText(DataHandler.currentZoneId.toString());
        }
        if (Locale.getDefault().getLanguage().equals("fr")) {
            titleLabel.setText(DataHandler.rb.getString("global") + " " + DataHandler.rb.getString("scheduling"));
            loginLabel.setText(DataHandler.rb.getString("login"));
            usernameLabel.setText(DataHandler.rb.getString("username"));
            passwordLabel.setText(DataHandler.rb.getString("password"));
            loginBtn.setText(DataHandler.rb.getString("login"));
            exitBtn.setText(DataHandler.rb.getString("exit"));
            locationLabel.setText(DataHandler.rb.getString("current") + " " + DataHandler.rb.getString("location") + ":");
            currentLocationLabel.setText("La France");
            zoneLabel.setText(DataHandler.rb.getString("current") + " " + DataHandler.rb.getString("zone") + ":");
            currentZoneIdLabel.setText(DataHandler.currentZoneId.toString());
        }
    }    
}
