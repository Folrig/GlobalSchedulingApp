/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utilities.DataHandler;

/**
 *
 * @author James Spencer
 */
public class AddApptViewController implements Initializable {
    Stage stage;
    Parent scene;
    
    @FXML private Label apptModLabel;
    @FXML private TextField locationTextField;
    @FXML private DatePicker startDatePicker;
    @FXML private ComboBox<?> startHourComboBox;
    @FXML private ComboBox<?> startMinuteComboBox;
    @FXML private ComboBox<?> startAmPmComboBox;
    @FXML private ComboBox<?> userIdComboBox;
    @FXML private TextField apptIdTextField;
    @FXML private ComboBox<?> contactComboBox;
    @FXML private DatePicker endDatePicker;
    @FXML private ComboBox<?> endHourComboBox;
    @FXML private ComboBox<?> endMinuteComboBox;
    @FXML private ComboBox<?> endAmPmComboBox;
    @FXML private ComboBox<?> custIdComboBox;
    @FXML private TextField titleTextField;
    @FXML private TextField typeTextField;
    @FXML private TextField descriptionTextField;
    @FXML private Button cancelButton;
    @FXML private Button addApptButton;
    
    @FXML
    void onAddApptButtonClicked(ActionEvent event) {

    }

    @FXML
    void onCancelButtonClicked(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Adding Appointment?");
        alert.setHeaderText("Return to main menu?");
        alert.setContentText("OK to return to main menu" + 
            "\n" + "Cancel to go back to adding appointment");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setTitle("Main Menu");
            scene = FXMLLoader.load(getClass().getResource("/view/MainWindowView.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            stage.centerOnScreen();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            apptIdTextField.setText(Integer.toString(DataHandler.getNextIdValue("Appointment")));
        } catch (SQLException ex) {
            Logger.getLogger(AddApptViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
