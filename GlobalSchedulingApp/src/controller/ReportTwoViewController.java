/** The package that controls changes and I/O in the GUI. */
package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import utilities.DataHandler;

/**
 * FXML Controller class
 *
 * @author James Spencer
 */
/** This class controls the GUI for displaying the second report style.
 *  Populates all information about the appointments for the selected contact.
 */
public class ReportTwoViewController implements Initializable {
    /** The stage that will be used to set up the next GUI scene. */
    Stage stage;
    /** The GUI window that will be shown upon exiting the customer add interface. */
    Parent scene;
    
    /** The combo box from which to select the contact ID. */
    @FXML private ComboBox<Contact> contactComboBox;
    /** The table view that will display all of the appointments for the contact selected. */
    @FXML private TableView<Appointment> scheduleTableView;
    /** The table column that displays the appointment ID of the appointments for the contact. */
    @FXML private TableColumn<Appointment, Integer> apptIdColumn;
    /** The table column that displays the title of the appointments for the contact. */    
    @FXML private TableColumn<Appointment, String> titleColumn;
    /** The table column that displays the type of the appointments for the contact. */
    @FXML private TableColumn<Appointment, String> typeColumn;
    /** The table column that displays the description of the appointments for the contact. */
    @FXML private TableColumn<Appointment, String> descriptionColumn;
    /** The table column that displays the local start date and time of the appointments for the contact. */    
    @FXML private TableColumn<Appointment, LocalDateTime> startDateColumn;
    /** The table column that displays the local end date and time of the appointments for the contact. */    
    @FXML private TableColumn<Appointment, LocalDateTime> endDateColumn;
    /** The table column that displays the customer ID of the appointments for the contact. */    
    @FXML private TableColumn<Appointment, Integer> custIdColumn;

    /** This method handles when the button is clicked to cancel creating a customer and return to the main menu.
     * @param event This is the mouse button click event that calls the method
     * @throws IOException Handles exceptions between I/O devices
     */    
    @FXML
    void onBackBtnClicked(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("Update Customers");
        scene = FXMLLoader.load(getClass().getResource("/view/MainWindowView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This method changes the table view to match the appointments with the corresponding contact ID.
     * @param event This is the mouse button click event that calls the method
     */
    @FXML
    void onContactComboBoxValueChange(ActionEvent event) {
        populateTableView(contactComboBox.getValue().getId());
    }

    /** Populates the report table view with all of the appointments for the selected contact ID.
     * @param contactId The ID number of the selected customer
     */
    public void populateTableView(int contactId) {
        ObservableList<Appointment> filteredAppts = FXCollections.observableArrayList();
        
        for (Appointment appt : (ObservableList<Appointment>)DataHandler.readAppointments()) {
            if (appt.getContactId() == contactId) {
                filteredAppts.add(appt);
            }
        }
        
        scheduleTableView.setItems(filteredAppts);
        apptIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        custIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }
    
    /** This method handles the GUI is first loaded and populates all relevant fields.
     * @param url This is the URL that is calling the initialize method
     * @param rb The corresponding resource bundle that the application is using
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        contactComboBox.setItems(DataHandler.readContacts());
        contactComboBox.getSelectionModel().select(0);
        populateTableView(1);
    }    
    
}
