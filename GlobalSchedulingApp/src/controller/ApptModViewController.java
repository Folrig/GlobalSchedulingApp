/** The package that controls changes and I/O in the GUI. */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import utilities.DataHandler;

/**
 * FXML Controller class
 *
 * @author James Spencer
 */
/** This class controls the GUI for modifying existing appointments. */
public class ApptModViewController implements Initializable {
    /** The stage that will be used to set up the next GUI scene. */
    Stage stage;
    /** The GUI window that will be shown upon exiting the customer add interface. */
    Parent scene;
    
    /** A list of all available hours that can be selected for an appointment. */
    ObservableList<String> hours = FXCollections.observableArrayList();
    /** A list of all available minutes that can be selected for an appointment. */  
    ObservableList<String> minutes = FXCollections.observableArrayList();
    /** A list of all available meridiem (AM/PM) that can be selected for an appointment. */ 
    ObservableList<String> meridiem = FXCollections.observableArrayList();
    
    /** The opening business hours that can be offset by zone to ensure appointment times are made correctly. */    
    ZonedDateTime businessOpenTimeUtc = ZonedDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.of(8,0), ZoneId.of("America/New_York"));
    /** The closing business hours that can be offset by zone to ensure appointment times are made correctly. */        
    ZonedDateTime businessCloseTimeUtc = ZonedDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.of(22, 0), ZoneId.of("America/New_York"));
    
    /** The text field used to input the location of the appointment to be modified. */
    @FXML private TextField locationTextField;
    /** The date picker used to select the starting month and day of the appointment to be modified. */
    @FXML private DatePicker startDatePicker;
    /** The combo box used to select the starting hour of the appointment to be modified. */
    @FXML private ComboBox<String> startHourComboBox;
    /** The combo box used to select the starting minute of the appointment to be modified. */
    @FXML private ComboBox<String> startMinuteComboBox;
    /** The combo box used to select the starting meridem (AM/PM) of the appointment to be modified. */
    @FXML private ComboBox<String> startAmPmComboBox;
    /** The text field that displays the ID of the appointment to be modified. */
    @FXML private TextField apptIdTextField;
    /** The date picker used to select the ending month and day of the appointment to be modified. */
    @FXML private DatePicker endDatePicker;
    /** The combo box used to select the ending hour of the appointment to be modified. */
    @FXML private ComboBox<String> endHourComboBox;
    /** The combo box used to select the ending minute of the appointment to be modified. */
    @FXML private ComboBox<String> endMinuteComboBox;
    /** The combo box used to select the meridiem (AM/PM) of the appointment to be modified. */
    @FXML private ComboBox<String> endAmPmComboBox;
    /** The combo box used to select the corresponding contact ID for the appointment to be modified. */
    @FXML private ComboBox<Contact> contactComboBox;
    /** The combo box used to select the corresponding user ID for the appointment to be modified. */
    @FXML private ComboBox<User> userIdComboBox;
    /** The combo box used to select the corresponding customer ID for the appointment to be modified. */
    @FXML private ComboBox<Customer> custIdComboBox;
    /** The text field used to input the title of the appointment to be modified. */
    @FXML private TextField titleTextField;
    /** The text field used to input the type of the appointment to be modified. */
    @FXML public TextField typeTextField;
    /** The text field used to input the description of the appointment to be modified. */
    @FXML private TextField descriptionTextField;

    /** This method handles when the button is clicked to cancel creating a customer and return to the main menu.
     * @param event This is the mouse button click event that calls the method
     * @throws IOException Handles exceptions between I/O devices
     */
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

   /** This method handles when the button is clicked to modify an existing appointment.
     * @throws IOException Handles exceptions between I/O devices
     * @throws SQLException Handles exceptions when using SQL queries to the database
     * @param event This is the mouse button click event that calls the method
     */
    @FXML
    void onModApptButtonClicked(ActionEvent event) throws SQLException, IOException {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Modifying Appointment?");
        confirmation.setHeaderText("Confirm Modifying Appointment");
        confirmation.setContentText("OK to Confirm" + 
            "\n" + "Cancel to go back to modifying appointment");

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.get() == ButtonType.OK) {
            int apptId = Integer.parseInt(apptIdTextField.getText());
            String title = titleTextField.getText();
            String description = descriptionTextField.getText();
            String location = locationTextField.getText();
            String type = typeTextField.getText();

            // Format the date & time inputs into a string
            // Then parse them to a LocalDateTime
            String hourStr = "";
            String minuteStr = startMinuteComboBox.getValue();
            
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

            DateTimeFormatter fullFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String startDateStr = startDatePicker.getValue().format(dateFormatter);
            String fullDateStr = startDateStr + " " + hourStr + ":" + minuteStr;
            // Parse the combo boxes to a LocalDateTime
            LocalDateTime startInputLdt = LocalDateTime.parse(fullDateStr, fullFormatter);
            // Find what the inputs would be for the system time zone
            ZonedDateTime localStartDateZdt = startInputLdt.atZone(ZoneId.systemDefault());
            // Find what the inputs would be in UTC
            ZonedDateTime UtcStartDateZdt = localStartDateZdt.withZoneSameInstant(ZoneId.of("UTC"));
            // Convert the UTC zone date to a LocalDateTime for UTC
            LocalDateTime UtcStartDateLdt = UtcStartDateZdt.toLocalDateTime();
            
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
            // Parse the combo boxes to a LocalDateTime
            LocalDateTime endInputLdt = LocalDateTime.parse(fullDateStr, fullFormatter);
            // Find what the inputs would be for the system time zone
            ZonedDateTime localEndDateZdt = endInputLdt.atZone(ZoneId.systemDefault());
            // Find what the inputs would be in UTC for comparison
            ZonedDateTime UtcEndDateZdt = localEndDateZdt.withZoneSameInstant(ZoneId.of("UTC"));
            // Convert the UTC zone date to a LocalDateTime for UTC to store
            LocalDateTime UtcEndDateLdt = UtcEndDateZdt.toLocalDateTime();
            int custId = custIdComboBox.getValue().getId();
            int userId = userIdComboBox.getValue().getId();
            int contactId = contactComboBox.getValue().getId();
            
            // Make sure end time is after start time and start time is before end time
            if (UtcEndDateLdt.isBefore(UtcStartDateLdt)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Time");
                alert.setHeaderText("Appointment end time must be after start time");
                alert.showAndWait();
                return;
            } else if (UtcStartDateLdt.isAfter(UtcEndDateLdt)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Time");
                alert.setHeaderText("Appointment start time must be before end time");
                alert.showAndWait();
                return;
            }
            
            // Loop through appointments to ensure there are no conflicts
            for (Appointment appt : (ObservableList<Appointment>)DataHandler.readAppointments()) {
                // Check if customer already has any appointments
                if (appt.getCustomerId() == custId) {
                    // Check if appointment dates are the same
                    if (appt.getStartTime().toLocalDate().equals(UtcStartDateLdt.toLocalDate()) &&
                        appt.getId() != apptId) {
                        // Check if the appointment times are overlapping
                        if (appt.getStartTime().toLocalTime().isBefore(UtcEndDateLdt.toLocalTime()) ||
                            appt.getEndTime().toLocalTime().isAfter(UtcStartDateLdt.toLocalTime())) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Invalid Time");
                            alert.setHeaderText("Customer already has an appointment during this time period");
                            alert.showAndWait();
                            return;
                        }
                    }
                    // Check if the appointment is before business hours
                    if (UtcStartDateZdt.withZoneSameInstant(ZoneId.of("America/New_York")).toLocalTime().isBefore(businessOpenTimeUtc.toLocalTime())) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Invalid Time");
                        alert.setHeaderText("Business hours are 8am - 10pm EST\n" + 
                                "The proposed appointment begins before opening business hours");
                        alert.showAndWait();
                        return;

                    // Check if the appointment is after business hours
                    } else if (UtcEndDateZdt.withZoneSameInstant(ZoneId.of("America/New_York")).toLocalTime().isAfter(businessCloseTimeUtc.toLocalTime())) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Invalid Time");
                        alert.setHeaderText("Business hours are 8am - 10pm EST\n" + 
                                "The proposed appointment ends after closing business hours");
                        alert.showAndWait();
                        return;
                    }
                }
            }

            // Create the appointment because time block is free for the customer
            DataHandler.updateAppointment(apptId, title, description, location, type, UtcStartDateLdt,
            UtcEndDateLdt, LocalDateTime.now(), DataHandler.currentUser.getName(), custId, userId, contactId);

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setTitle("Main Menu");
            scene = FXMLLoader.load(getClass().getResource("/view/MainWindowView.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            stage.centerOnScreen();
        }
    }
    
    /** This method handles the GUI is first loaded and populates all relevant fields.
     * @param url This is the URL that is calling the initialize method
     * @param rb The corresponding resource bundle that the application is using
     */    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set up all GUI controls with base values
        // Then display them with the values of the appointment we want to modify
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
        if (DataHandler.apptToModify.getStartTime().toLocalTime().getHour() > 12) {
            startHourComboBox.getSelectionModel().select(String.valueOf((DataHandler.apptToModify.getStartTime().toLocalTime().getHour() - 12)));
        } else {
            startHourComboBox.getSelectionModel().select(String.valueOf(DataHandler.apptToModify.getStartTime().toLocalTime().getHour()));
        }
        startMinuteComboBox.setItems(minutes);
        String startMinute = String.valueOf(DataHandler.apptToModify.getStartTime().toLocalTime().getMinute());
        if (startMinute.equals("0") || startMinute.equals("5")) {
            startMinute = "0" + startMinute;
        }
        startMinuteComboBox.getSelectionModel().select(startMinute);
        startAmPmComboBox.setItems(meridiem);
        if (DataHandler.apptToModify.getStartTime().toLocalTime().getHour() > 12) {
            startAmPmComboBox.getSelectionModel().select("PM");
        } else {
            startAmPmComboBox.getSelectionModel().select("AM");
        }
        endDatePicker.setValue(DataHandler.apptToModify.getEndTime().toLocalDate());
        endHourComboBox.setItems(hours);
        if (DataHandler.apptToModify.getEndTime().toLocalTime().getHour() > 12) {
            endHourComboBox.getSelectionModel().select(String.valueOf((DataHandler.apptToModify.getEndTime().toLocalTime().getHour() - 12)));
        } else {
            endHourComboBox.getSelectionModel().select(String.valueOf(DataHandler.apptToModify.getEndTime().toLocalTime().getHour()));
        }
        endMinuteComboBox.setItems(minutes);
        String endMinute = String.valueOf(DataHandler.apptToModify.getEndTime().toLocalTime().getMinute());
        if (endMinute.equals("0") || endMinute.equals("5")) {
            endMinute = "0" + endMinute;
        }
        endMinuteComboBox.getSelectionModel().select(endMinute);
        endAmPmComboBox.setItems(meridiem);
        if (DataHandler.apptToModify.getEndTime().toLocalTime().getHour() > 12) {
            endAmPmComboBox.getSelectionModel().select("PM");
        } else {
            endAmPmComboBox.getSelectionModel().select("AM");
        }
    }      
}
