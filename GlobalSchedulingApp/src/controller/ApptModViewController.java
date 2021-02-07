/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Contact;
import model.Customer;
import model.User;
import utilities.DataHandler;

/**
 * FXML Controller class
 *
 * @author James Spencer
 */
public class ApptModViewController implements Initializable {
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
    @FXML private ComboBox<?> endHourComboBox;
    @FXML private ComboBox<?> endMinuteComboBox;
    @FXML private ComboBox<?> endAmPmComboBox;
    @FXML private ComboBox<?> contactComboBox;
    @FXML private ComboBox<User> userIdComboBox;
    @FXML private ComboBox<Customer> custIdComboBox;
    @FXML private TextField titleTextField;
    @FXML private TextField typeTextField;
    @FXML private TextField descriptionTextField;
    @FXML private Button cancelButton;
    @FXML private Button modApptButton;

    @FXML
    void onCancelButtonClicked(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Modifying Appointment?");
        alert.setHeaderText("Return to main menu?");
        alert.setContentText("OK to return to main menu" + 
            "\n" + "Cancel to go back to modifying appointment");

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

    @FXML
    void onModApptButtonClicked(ActionEvent event) {

    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int contactIndex = 0;
        int custIndex = 0;
        int userIndex = 0;
                
        for (Contact contact : DataHandler.allContacts) {
            if (contact.getId() == DataHandler.apptToModify.getContactId()) {
                contactIndex = DataHandler.allContacts.indexOf(contact);
                break;
            }
        }     
        for (Customer cust : DataHandler.allCustomers) {
            if (cust.getId() == DataHandler.apptToModify.getCustomerId()) {
                custIndex = DataHandler.allCustomers.indexOf(cust);
                break;
            }
        }
        for (User user : DataHandler.allUsers) {
            if (user.getId() == DataHandler.apptToModify.getUserId()) {
                userIndex = DataHandler.allUsers.indexOf(user);
                break;
            }
        }
        
        for (int i = 1; i <= 12; i++) {
            hours.add(String.valueOf(i));
            if (i < 12) {
                minutes.add(String.valueOf(i * 5));
            } else {
                minutes.add("00");
            }
        }
        apptIdTextField.setText(Integer.toString(DataHandler.apptToModify.getId()));
        titleTextField.setText(DataHandler.apptToModify.getTitle());
        locationTextField.setText(DataHandler.apptToModify.getLocation());
        typeTextField.setText(DataHandler.apptToModify.getType());
        descriptionTextField.setText((DataHandler.apptToModify.getDescription()));
        contactComboBox.setItems(DataHandler.readContacts());
        contactComboBox.getSelectionModel().select(contactIndex);
        userIdComboBox.setItems(DataHandler.readUsers());
        userIdComboBox.getSelectionModel().select(userIndex);
        custIdComboBox.setItems(DataHandler.readCustomers());
        custIdComboBox.getSelectionModel().select(custIndex);
        startDatePicker.setValue(DataHandler.apptToModify.getStartTime().toLocalDate());
        startHourComboBox.setItems(hours);
        
        // startHourcomboBox.getSelectionModel().select()
    }      
}
