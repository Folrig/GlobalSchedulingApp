/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import model.Contact;
import model.Customer;
import model.User;
import utilities.DataHandler;

/**
 *
 * @author James Spencer
 */
public class AddApptViewController implements Initializable {
    Stage stage;
    Parent scene;
    ObservableList<String> hours = FXCollections.observableArrayList();
    ObservableList<String> minutes = FXCollections.observableArrayList();
    ObservableList<String> meridiem = FXCollections.observableArrayList();
    
    @FXML private Label apptModLabel;
    @FXML private TextField locationTextField;
    @FXML private DatePicker startDatePicker;
    @FXML private ComboBox<String> startHourComboBox;
    @FXML private ComboBox<String> startMinuteComboBox;
    @FXML private ComboBox<String> startAmPmComboBox;
    @FXML private TextField apptIdTextField;
    @FXML private DatePicker endDatePicker;
    @FXML private ComboBox<String> endHourComboBox;
    @FXML private ComboBox<String> endMinuteComboBox;
    @FXML private ComboBox<String> endAmPmComboBox;
    @FXML private ComboBox<Contact> contactComboBox;
    @FXML private ComboBox<User> userIdComboBox;
    @FXML private ComboBox<Customer> custIdComboBox;
    @FXML private TextField titleTextField;
    @FXML private TextField typeTextField;
    @FXML private TextField descriptionTextField;
    @FXML private Button cancelButton;
    @FXML private Button addApptButton;
    
    @FXML
    void onAddApptButtonClicked(ActionEvent event) throws SQLException, IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Modifying Appointment?");
        alert.setHeaderText("Confirm Modifying Appointment");
        alert.setContentText("OK to Confirm" + 
            "\n" + "Cancel to go back to modifying appointment");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            String title = titleTextField.getText();
            String description = descriptionTextField.getText();
            String location = locationTextField.getText();
            String type = typeTextField.getText();

            // Format the date & time inputs into a string
            // Then parse them to a LocalDateTime
            String fullDateStr = null;
            String hourStr = null;
            String minuteStr = null;
            
            if (startAmPmComboBox.getValue().equals("PM") && !startHourComboBox.getValue().equals("12")) {
                hourStr = String.valueOf(Integer.parseInt(startHourComboBox.getValue()) + 12);
            } else if (startAmPmComboBox.getValue().equals("AM") && startHourComboBox.getValue().equals("12)")) {
                hourStr = "0";
            } else {
                hourStr = startHourComboBox.getValue();
            }
            
            if (Integer.parseInt(hourStr) < 10) {
                hourStr = "0" + hourStr;
            }

            minuteStr = startMinuteComboBox.getValue();
            DateTimeFormatter fullFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String startDateStr = startDatePicker.getValue().format(dateFormatter);
            fullDateStr = startDateStr + " " + hourStr + ":" + minuteStr;
            LocalDateTime startDate = LocalDateTime.parse(fullDateStr, fullFormatter);
            
            String endDateStr = endDatePicker.getValue().format(dateFormatter);
            if (endAmPmComboBox.getValue().equals("PM") && !endHourComboBox.getValue().equals("12")) {
                hourStr = String.valueOf(Integer.parseInt(endHourComboBox.getValue()) + 12);
            } else if (endAmPmComboBox.getValue().equals("AM") && endHourComboBox.getValue().equals("12)")) {
                hourStr = "0";
            } else {
                hourStr = endHourComboBox.getValue();
            }
            
            if (Integer.parseInt(hourStr) < 10) {
                hourStr = "0" + hourStr;
            }
            
            minuteStr = endMinuteComboBox.getValue();
            fullDateStr = endDateStr + " " + hourStr + ":" + minuteStr;
            LocalDateTime endDate = LocalDateTime.parse(fullDateStr, fullFormatter);
            int custId = custIdComboBox.getValue().getId();
            int userId = userIdComboBox.getValue().getId();
            int contactId = contactComboBox.getValue().getId();

            DataHandler.createAppointment(title, description, location, type, startDate, endDate, 
                    LocalDateTime.now(), DataHandler.currentUser, LocalDateTime.now(), 
                    DataHandler.currentUser, custId, userId, contactId);
            
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setTitle("Main Menu");
            scene = FXMLLoader.load(getClass().getResource("/view/MainWindowView.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            stage.centerOnScreen();
        }
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
        // Set up all GUI controls with base values
        try {
            apptIdTextField.setText(Integer.toString(DataHandler.getNextIdValue("Appointment")));
            minutes.add("00");
            for (int i = 1; i <= 12; i++) {
                hours.add(String.valueOf(i));
                if (i < 12) {
                    if (i == 1) {
                        minutes.add("05");
                    } else {
                        minutes.add(String.valueOf(i * 5));
                    }
                }
            }
            meridiem.add("AM");
            meridiem.add("PM");
            contactComboBox.setItems(DataHandler.readContacts());
            userIdComboBox.setItems(DataHandler.readUsers());
            custIdComboBox.setItems(DataHandler.readCustomers());
            startDatePicker.setValue(LocalDateTime.now().toLocalDate());
            startHourComboBox.setItems(hours);
            startMinuteComboBox.setItems(minutes);
            startAmPmComboBox.setItems(meridiem);
            endDatePicker.setValue(LocalDateTime.now().toLocalDate());
            endHourComboBox.setItems(hours);
            endMinuteComboBox.setItems(minutes);
            endAmPmComboBox.setItems(meridiem);
        } catch (SQLException ex) {
            Logger.getLogger(AddApptViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
