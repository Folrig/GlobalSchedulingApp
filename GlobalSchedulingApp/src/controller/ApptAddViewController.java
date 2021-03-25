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
import model.Appointment;
import utilities.DataHandler;

/**
 * FXML Controller class
 * 
 * @author James Spencer
 */

/** This class controls the GUI for creating new appointments. */
public class ApptAddViewController implements Initializable {
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
    
    /** The text field used to input the location of the new appointment. */
    @FXML private TextField locationTextField;
    /** The date picker used to select the starting month and day of the new appointment. */
    @FXML private DatePicker startDatePicker;
    /** The combo box used to select the starting hour of the new appointment time. */
    @FXML private ComboBox<String> startHourComboBox;
    /** The combo box used to select the starting minute of the new appointment time. */
    @FXML private ComboBox<String> startMinuteComboBox;
    /** The combo box used to select the starting meridem (AM/PM) of the new appointment time. */
    @FXML private ComboBox<String> startAmPmComboBox;
    /** The text field that displays the ID of the new appointment. */
    @FXML private TextField apptIdTextField;
    /** The date picker used to select the ending month and day of the new appointment. */
    @FXML private DatePicker endDatePicker;
    /** The combo box used to select the ending hour of the new appointment time. */
    @FXML private ComboBox<String> endHourComboBox;
    /** The combo box used to select the ending minute of the new appointment time. */
    @FXML private ComboBox<String> endMinuteComboBox;
    /** The combo box used to select the meridiem (AM/PM) of the new appointment time. */
    @FXML private ComboBox<String> endAmPmComboBox;
    /** The combo box used to select the corresponding contact ID for the new appointment. */
    @FXML private ComboBox<Contact> contactComboBox;
    /** The combo box used to select the corresponding user ID for the new appointment. */
    @FXML private ComboBox<User> userIdComboBox;
    /** The combo box used to select the corresponding customer ID for the new appointment. */
    @FXML private ComboBox<Customer> custIdComboBox;
    /** The text field used to input the title of the new appointment. */
    @FXML private TextField titleTextField;
    /** The text field used to input the type of the new appointment. */
    @FXML public TextField typeTextField;
    /** The text field used to input the description of the new appointment. */
    @FXML private TextField descriptionTextField;
    
    /** This method handles when the button is clicked to cancel creating a customer and return to the main menu.
     * @param event This is the mouse button click event that calls the method
     * @throws IOException Handles exceptions between I/O devices
     */
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

   /** This method handles when the button is clicked to create a new appointment.
     * @throws IOException Handles exceptions between I/O devices
     * @throws SQLException Handles exceptions when using SQL queries to the database
     * @param event This is the mouse button click event that calls the method
     */
    @FXML
    void onAddApptButtonClicked(ActionEvent event) throws SQLException, IOException {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Modifying Appointment?");
        confirmation.setHeaderText("Confirm Modifying Appointment");
        confirmation.setContentText("OK to Confirm" + 
            "\n" + "Cancel to go back to modifying appointment");

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.get() == ButtonType.OK) {
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
                    if (appt.getStartTime().toLocalDate().equals(UtcStartDateLdt.toLocalDate())) {
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
            DataHandler.createAppointment(title, description, location, type, UtcStartDateLdt,
            UtcEndDateLdt, LocalDateTime.now(), DataHandler.currentUser.getName(),
            LocalDateTime.now(), DataHandler.currentUser.getName(), custId, userId, contactId);

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
            contactComboBox.getSelectionModel().select(0);
            userIdComboBox.setItems(DataHandler.readUsers());
            userIdComboBox.getSelectionModel().select(0);
            custIdComboBox.setItems(DataHandler.readCustomers());
            custIdComboBox.getSelectionModel().select(0);
            startDatePicker.setValue(LocalDateTime.now().toLocalDate());
            startHourComboBox.setItems(hours);
            startHourComboBox.getSelectionModel().select(11); // Set default start hour to 10
            startMinuteComboBox.setItems(minutes);
            startMinuteComboBox.getSelectionModel().select(0); // Set default minutes to 00
            startAmPmComboBox.setItems(meridiem);
            startAmPmComboBox.getSelectionModel().select(0); // Set default start meridiem to AM
            endDatePicker.setValue(LocalDateTime.now().toLocalDate());
            endHourComboBox.setItems(hours);
            endHourComboBox.getSelectionModel().select(11); // Set default end hour to 10
            endMinuteComboBox.setItems(minutes);
            endMinuteComboBox.getSelectionModel().select(0); // Set default end minutes to 00
            endAmPmComboBox.setItems(meridiem);
            endAmPmComboBox.getSelectionModel().select(0); // Set default end meridiem to AM
        } catch (SQLException ex) {
            Logger.getLogger(ApptAddViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
