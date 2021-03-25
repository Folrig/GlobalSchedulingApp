/** The package that controls changes and I/O in the GUI. */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import utilities.DataHandler;

/**
 * FXML Controller class
 *
 * @author James Spencer
 */
/** This class controls the GUI for displaying the main menu.
 *  Populates all appointment information for display and modification.
 */
public class MainWindowViewController implements Initializable {
    /** The stage that will be used to set up the next GUI scene. */
    Stage stage;
    /** The GUI window that will be shown upon exiting the customer add interface. */
    Parent scene;
    
    /** The table view that displays all of the appointment information */
    @FXML private TableView<Appointment> scheduleTableView;
    /** The table column that displays each appointment ID number. */
    @FXML private TableColumn<Appointment, Integer> apptIdColumn;
    /** The table column that displays each appointment title. */
    @FXML private TableColumn<Appointment, String> titleColumn;
    /** The table column that displays each appointment description. */    
    @FXML private TableColumn<Appointment, String> descriptionColumn;
    /** The table column that displays each appointment location. */    
    @FXML private TableColumn<Appointment, String> locationColumn;
    /** The table column that displays each appointment contact ID. */    
    @FXML private TableColumn<Appointment, String> contactColumn;
    /** The table column that displays each appointment type. */
    @FXML private TableColumn<Appointment, String> typeColumn;
    /** The table column that displays each appointment local start time. */    
    @FXML private TableColumn<Appointment, LocalDateTime> startColumn;
    /** The table column that displays each appointment local end time. */    
    @FXML private TableColumn<Appointment, LocalDateTime> endColumn;
    /** The table column that displays each appointment customer ID. */    
    @FXML private TableColumn<Appointment, String> custIdColumn;

    /** This method handles when the button is clicked to load the GUI screen for adding a new appointment.
     * @param event This is the mouse button click event that calls the method
     * @throws IOException Handles exceptions between I/O devices
     */
    @FXML
    void onApptAddButtonClicked(ActionEvent event) throws IOException {
        DataHandler.addAppointment = true;
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("Add Appointment");
        scene = FXMLLoader.load(getClass().getResource("/view/ApptAddView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    /** This method handles when the button is clicked to load the GUI screen for modifying the appointment selected in the table view.
     * @param event This is the mouse button click event that calls the method
     * @throws IOException Handles exceptions between I/O devices
     */
    @FXML
    void onUpdateApptButtonClicked(ActionEvent event) throws IOException {
        DataHandler.addAppointment = false;
        DataHandler.apptToModify = (scheduleTableView.getSelectionModel().getSelectedItem());
        if (DataHandler.apptToModify != null) {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setTitle("Update Appointment");
            scene = FXMLLoader.load(getClass().getResource("/view/ApptModView.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            stage.centerOnScreen();
        }
    }

    /** This method handles when the button is clicked to load the GUI screen for adding, updating, or deleting a customer.
     * @param event This is the mouse button click event that calls the method
     * @throws IOException Handles exceptions between I/O devices
     */
    @FXML
    void onCustAudButtonClicked(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("Update Customers");
        scene = FXMLLoader.load(getClass().getResource("/view/CustModView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This method handles when the button is clicked to delete the appointment selected in the table view.
     * @param event This is the mouse button click event that calls the method
     * @throws SQLException Handles exceptions when using SQL queries to the database
     */
    @FXML
    void onDeleteApptButtonClicked(ActionEvent event) throws SQLException {
        if(scheduleTableView.getSelectionModel().getSelectedItem() != null){
            Appointment apptToDelete = scheduleTableView.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Okay To Delete?");
            alert.setHeaderText("Deleting Appointment #" + String.valueOf(apptToDelete.getId()) +
                    "\nType Of Appointment: " + apptToDelete.getType());
            alert.setContentText("Click OK to confirm deletion or cancel to go back.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                DataHandler.deleteAppointment(apptToDelete.getId());
                populateScheduleTableView();
            }
        }
    }

    /** This method handles when the button is clicked load the GUI screen for the first report type.
     * @param event This is the mouse button click event that calls the method
     * @throws IOException Handles exceptions between I/O devices
     */
    @FXML
    void onReportOneBtnclicked(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("Appointment By Type And Month Report");
        scene = FXMLLoader.load(getClass().getResource("/view/ReportOneView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This method handles when the button is clicked load the GUI screen for the second report type.
     * @param event This is the mouse button click event that calls the method
     * @throws IOException Handles exceptions between I/O devices
     */    
    @FXML
    void onReportTwoBtnClicked(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("Schedules By Contact");
        scene = FXMLLoader.load(getClass().getResource("/view/ReportTwoView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This method handles when the button is clicked load the GUI screen for the third report type.
     * @param event This is the mouse button click event that calls the method
     * @throws IOException Handles exceptions between I/O devices
     */    
    @FXML
    void onReportThreeBtnClicked(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("Summary Of All Items");
        scene = FXMLLoader.load(getClass().getResource("/view/ReportThreeView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This method handles when the radio button is toggled adjust the table view to show ALL appointments.
     * @param event This is the mouse button click event that calls the method
     */    
    @FXML
    void onAllApptRadioBtnTog(ActionEvent event) {
        populateScheduleTableView();
    }

    /** This method handles when the radio button is toggled adjust the table view to show appointments only for the current month.
     * @param event This is the mouse button click event that calls the method
     */      
    @FXML
    void onMonthlyRdoBtnTog(ActionEvent event) {
        ObservableList<Appointment> monthlyAppointments = FXCollections.observableArrayList();
        for (Appointment appt : (ObservableList<Appointment>)DataHandler.readAppointments()){
            if (appt.getStartTime().getMonth().equals(LocalDateTime.now().getMonth())) {
                monthlyAppointments.add(appt);
            }
        }
        scheduleTableView.setItems(monthlyAppointments);
        apptIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        custIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }

    /** This method handles when the radio button is toggled adjust the table view to show appointments only for the current week.
     * @param event This is the mouse button click event that calls the method
     */ 
    @FXML
    void onWeeklyRadioBtnTog(ActionEvent event) {
        ObservableList<Appointment> weeklyAppointments = FXCollections.observableArrayList();
 
        DayOfWeek firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
        DayOfWeek lastDayOfWeek = DayOfWeek.of(((firstDayOfWeek.getValue() + 5) % DayOfWeek.values().length) + 1);
        
        LocalDate startOfWeek = LocalDate.now(ZoneId.systemDefault()).with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
        LocalDate endOfWeek = LocalDate.now(ZoneId.systemDefault()).with(TemporalAdjusters.nextOrSame(lastDayOfWeek));
        
        
        for (Appointment appt : (ObservableList<Appointment>)DataHandler.readAppointments()){
            if ((appt.getStartTime().toLocalDate().isEqual(startOfWeek) || appt.getStartTime().toLocalDate().isAfter(startOfWeek)) &&
                    (appt.getEndTime().toLocalDate().isEqual(endOfWeek) || appt.getEndTime().toLocalDate().isBefore(endOfWeek))){
                weeklyAppointments.add(appt);
            }
        }
        scheduleTableView.setItems(weeklyAppointments);
        apptIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        custIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }
    
    /** This method populates the schedule table view with all existing appointments and their information. */
    private void populateScheduleTableView() {
        scheduleTableView.setItems(DataHandler.readAppointments());
        apptIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        custIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }

    /** This method handles the GUI is first loaded and populates all relevant fields.
     * @param url This is the URL that is calling the initialize method
     * @param rb The corresponding resource bundle that the application is using
     */    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            DataHandler.setAllAppointments();
            populateScheduleTableView();
            for (Appointment appt : (ObservableList<Appointment>)DataHandler.readAppointments()) {
                ZonedDateTime apptStartTimeZdt = appt.getStartTime().atZone(ZoneId.systemDefault());
                ZonedDateTime apptUtcStartTimeZdt = apptStartTimeZdt.withZoneSameInstant(ZoneId.of("UTC"));
                LocalDateTime apptUtcStartTimeLdt = apptUtcStartTimeZdt.toLocalDateTime();
                
                if (appt.getUserId() == DataHandler.currentUser.getId() &&
                        apptUtcStartTimeLdt.toLocalDate().equals(LocalDate.now(ZoneOffset.UTC)) &&
                        apptUtcStartTimeLdt.toLocalTime().minusMinutes(15).isBefore(LocalTime.now(ZoneOffset.UTC)) &&
                        !apptUtcStartTimeLdt.toLocalTime().isBefore(LocalTime.now(ZoneOffset.UTC))) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Upcoming Appointments");
                    alert.setHeaderText("You Have An Appointment Soon");
                    LocalDate apptDate = appt.getStartTime().toLocalDate();
                    String formattedDate = apptDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
                    String apptTime = appt.getStartTime().toLocalTime().toString();
                    // LocalDateTime tempDateTime = LocalDateTime.parse(apptTime, DateTimeFormatter.ISO_DATE_TIME);
                    String content = "You have an upcoming appointment with ID number " + appt.getId() +
                            " on " + formattedDate + " at " + apptTime;
                            // tempTime.format(DateTimeFormatter.ofPattern("HH:mm"));
                    alert.setContentText(content);
                    alert.show();
                    return;
                }
            }
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Upcoming Appointments");
            alert.setHeaderText("No Upcoming Appointments");
            String content = "You currently have no appointments coming soon";
            alert.setContentText(content);
            alert.show();
        } catch (SQLException ex) {
            Logger.getLogger(MainWindowViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}