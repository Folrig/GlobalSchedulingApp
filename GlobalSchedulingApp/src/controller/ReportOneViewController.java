/** The package that controls changes and I/O in the GUI. */
package controller;


import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
/** This class controls the GUI for displaying the first report style.
 *  Populates all information about the quantity and type of appointments.
 */
public class ReportOneViewController implements Initializable {
    /** The stage that will be used to set up the next GUI scene. */
    Stage stage;
    /** The GUI window that will be shown upon exiting the customer add interface. */
    Parent scene;
    
    /** The table view that displays all of the quantity and type information. */    
    @FXML private TableView<AppointmentByType> typeTableView;
    /** The table column that displays each appointment type quantity. */    
    @FXML private TableColumn<AppointmentByType, Integer> typeQtyColumn;
    /** The table column that displays each appointment type. */    
    @FXML private TableColumn<AppointmentByType, String> apptTypeColumn;
    /** The table view that displays all of the quantity and month information. */      
    @FXML private TableView<AppointmentByMonth> monthTableView;
    /** The table column that displays each appointment month quantity. */    
    @FXML private TableColumn<AppointmentByMonth, Integer> monthQtyColumn;
    /** The table column that displays each appointment month. */    
    @FXML private TableColumn<AppointmentByMonth, String> monthColumn;

    /** The table view that displays all of the quantity and type information */      
    ObservableList<AppointmentByType> allApptByType = FXCollections.observableArrayList();
    /** The table view that displays all of the quantity and type information */      
    ObservableList<AppointmentByMonth> allApptByMonth= FXCollections.observableArrayList();
    
    /** A subclass that holds data of appointment quantity by type. */
    public class AppointmentByType {
        /** This integer data holds the quantity of appointments of the corresponding type. */
        private int qty;
        /** This String data holds the type of appointment. */
        private String type;
        
        /** The constructor for the AppointmentByType class.
         * @param qty The quantity of appointments that match the type of appointment
         * @param type The type of appointment
         */
        public AppointmentByType (int qty, String type) {
            this.qty = qty;
            this.type = type;
        }
        
        /** A getter method for the quantity member.
         * @return The quantity of the appointments of the corresponding type
         */
        public int getQty() {
            return qty;
        }

        /** A setter method for the quantity member.
         * @param qty The quantity of the appointments of the corresponding type
         */
        public void setQty(int qty) {
            this.qty = qty;
        }

        /** A getter method for the type member.
         * @return The type of the appointment
         */
        public String getType() {
            return type;
        }

        /** A setter method for the type member.
         * @param type The type of the appointment
         */
        public void setType(String type) {
            this.type = type;
        }
    }
    
    public class AppointmentByMonth {
        /** This integer data holds the quantity of appointments of the corresponding month. */        
        private int qty;
        /** This String data holds the month of appointment. */        
        private String month;
        
        /** The constructor for the AppointmentByMonth class.
         * @param qty The quantity of appointments that match the type of appointment
         * @param month The month of the appointments
         */        
        public AppointmentByMonth (int qty, String month) {
            this.qty = qty;
            this.month = month;
        }
        
        /** A getter method for the quantity member.
         * @return The quantity of the appointments of the corresponding type
         */        
        public int getQty() {
            return qty;
        }

        /** A setter method for the quantity member.
         * @param qty The quantity of the appointments of the corresponding type
         */
        public void setQty(int qty) {
            this.qty = qty;
        }

        /** A getter method for the month member.
         * @return The month that the appointments fall in
         */        
        public String getMonth() {
            return month;
        }

        /** A setter method for the quantity member.
         * @param month The month that the appointments fall in
         */        
        public void setMonth(String month) {
            this.month = month;
        }
    }
    
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
    
    /** This method populates the report table view with all existing appointment quantities per type and month.
     * This method utilizes two lambda expressions in order to utilize functional programming and minimize
     * the amount of maintenance necessary on a line of code by using the streaming functions of hash maps,
     * both for appointments by type and appointments by month
     */    
    private void populateReportTableView() {
        HashMap<String, Integer> apptByType = new HashMap<>();
        HashMap<String, Integer> apptByMonth = new HashMap<>();
        
        for (Appointment appt : (ObservableList<Appointment>)DataHandler.readAppointments()) {     
            if (apptByType.containsKey(appt.getType())) {
                apptByType.replace(appt.getType(), apptByType.get(appt.getType()) + 1);
            } else {
                apptByType.put(appt.getType(), 1);
            }
            if (apptByMonth.containsKey(appt.getStartTime().getMonth().toString())) {
                apptByMonth.replace(appt.getStartTime().getMonth().toString(), apptByMonth.get(appt.getStartTime().getMonth().toString()) + 1);
            } else {
                apptByMonth.put(appt.getStartTime().getMonth().toString(), 1);
            }
        }
        
        apptByType.keySet().stream().map((key) -> new AppointmentByType (apptByType.get(key), key)).forEachOrdered((newApptByType) -> {
            allApptByType.add(newApptByType);
        });
        apptByMonth.keySet().stream().map((key) -> new AppointmentByMonth (apptByMonth.get(key), key)).forEachOrdered((newApptByMonth) -> {
            allApptByMonth.add(newApptByMonth);
        });

        typeTableView.setItems(allApptByType);
        typeQtyColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));
        apptTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        monthTableView.setItems(allApptByMonth);
        monthQtyColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
    }
    
    /** This method handles the GUI is first loaded and populates all relevant fields.
     * @param url This is the URL that is calling the initialize method
     * @param rb The corresponding resource bundle that the application is using
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateReportTableView();
    }     
}
