/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import utilities.DataHandler;

/**
 * FXML Controller class
 *
 * @author James Spencer
 */
public class MainWindowViewController implements Initializable {
    Stage stage;
    Parent scene;
    
    @FXML private TableView<Appointment> scheduleTableView;
    @FXML private TableColumn<Appointment, Integer> apptIdColumn;
    @FXML private TableColumn<Appointment, String> titleColumn;
    @FXML private TableColumn<Appointment, String> descriptionColumn;
    @FXML private TableColumn<Appointment, String> locationColumn;
    @FXML private TableColumn<Appointment, String> contactColumn;
    @FXML private TableColumn<Appointment, String> typeColumn;
    @FXML private TableColumn<Appointment, LocalDateTime> startColumn;
    @FXML private TableColumn<Appointment, LocalDateTime> endColumn;
    @FXML private TableColumn<Appointment, String> custIdColumn;
    @FXML private Button apptAddButton;
    @FXML private Button updateApptButton;
    @FXML private Button custAudButton;
    @FXML private Button deleteApptButton;
    @FXML private RadioButton weeklyViewRadioButton;
    @FXML private ToggleGroup viewRadioToggleGroup;
    @FXML private RadioButton monthlyViewRadioButton;
    @FXML private Button reportOneButton;
    @FXML private Button reportTwoButton;
    @FXML private Button reportThreeButton;

    @FXML
    void onApptAddButtonClicked(ActionEvent event) throws IOException {
        DataHandler.addAppointment = true;
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("Add Appointment");
        scene = FXMLLoader.load(getClass().getResource("/view/AddApptView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
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

    @FXML
    void onCustAudButtonClicked(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("Update Customers");
        scene = FXMLLoader.load(getClass().getResource("/view/CustModView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onDeleteApptButtonClicked(ActionEvent event) throws SQLException {
        if(scheduleTableView.getSelectionModel().getSelectedItem() != null){
            Appointment apptToDelete = scheduleTableView.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Okay To Delete?");
            alert.setHeaderText("Deleting Appointment #" + String.valueOf(apptToDelete.getId()) +
                    "\n" + apptToDelete.getType());
            alert.setContentText("Click OK to confirm deletion or cancel to go back.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                DataHandler.deleteAppointment(apptToDelete.getId());
                populateScheduleTableView();
            }
        }
    }

    @FXML
    void onReportOneBtnclicked(ActionEvent event) {

    }

    @FXML
    void onReportTwoBtnClicked(ActionEvent event) {

    }
    
    @FXML
    void onReportThreeBtnClicked(ActionEvent event) {

    }

    @FXML
    void onMonthlyRdoBtnTog(ActionEvent event) {

    }
    
    @FXML
    void onWeeklyRadioBtnTog(ActionEvent event) {

    }
    
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

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            DataHandler.setAllAppointments();
            populateScheduleTableView();
            for (Appointment appt : (ObservableList<Appointment>)DataHandler.readAppointments()) {
                if (appt.getUserId() == DataHandler.currentUser.getId() &&
                        appt.getStartTime().equals(LocalDateTime.now(ZoneOffset.UTC)) &&
                        appt.getStartTime().minusMinutes(15).isBefore(LocalDateTime.now(ZoneOffset.UTC)) &&
                        !appt.getStartTime().isBefore(LocalDateTime.now(ZoneOffset.UTC))) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Upcoming Appointments");
                    alert.setHeaderText("You Have An Appointment Soon");
                    LocalDate apptDate = appt.getStartTime().toLocalDate();
                    String formattedDate = apptDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
                    String apptTime = appt.getStartTime().toLocalTime().toString();
                    LocalDateTime tempDateTime = LocalDateTime.parse(apptTime, DateTimeFormatter.ISO_DATE_TIME);
                    String content = "You have an upcoming appointment with ID number " + appt.getId() +
                            " on " + formattedDate + " at " + 
                            tempDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
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