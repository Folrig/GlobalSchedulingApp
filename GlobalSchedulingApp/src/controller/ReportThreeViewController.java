/** The package that controls changes and I/O in the GUI. */
package controller;

import java.io.IOException;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;
import model.User;
import utilities.DataHandler;

/**
 * FXML Controller class
 *
 * @author James Spencer
 */
/** This class controls the GUI for displaying the third report style.
 *  Populates all information about the quantity and type of appointments.
 */
public class ReportThreeViewController implements Initializable {
    /** The stage that will be used to set up the next GUI scene. */
    Stage stage;
    /** The GUI window that will be shown upon exiting the customer add interface. */
    Parent scene;
    
    /** A list of all the quantities of every model type in the database. */
    ObservableList<SummaryItem> allSummaryItems = FXCollections.observableArrayList();
    
    /** The table view that displays all of the quantities of every model type in the database. */ 
    @FXML private TableView<SummaryItem> summaryTableView;
    /** The table column that displays each model that is in the database. */       
    @FXML private TableColumn<SummaryItem, String> typeColumn;
    /** The table column that displays the quantity of every model in the database. */       
    @FXML private TableColumn<SummaryItem, Integer> quantityColumn;

    /** A subclass that holds data of every model and how many quantities that exist in the database. */
    public class SummaryItem {
        /** The String data of the model. */
        private String name;
        /** The integer data type that holds how many of each model exist in the database. */
        private Integer quantity;
        
        /** The constructor for the AppointmentByMonth class.
         * @param name The name of the model
         * @param qty How many instances of the model exist
         */
        public SummaryItem(String name, Integer qty) {
            this.name = name;
            this.quantity = qty;
        }
        
        /** A getter method for the name member.
         * @return The name of the model
         */
        public String getName() {
            return name;
        }

        /** A setter method for the name member.
         * @param name The name of the model that exists
         */        
        public void setName(String name) {
            this.name = name;
        }

        /** A getter method for the quantity member.
         * @return The quantity of instances that exist for the model
         */
        public Integer getQuantity() {
            return quantity;
        }
        
        /** A setter method for the quantity member.
         * @param quantity The quantity of instances that exist for the model
         */
        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
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
    
    /** This method handles the GUI is first loaded and populates all relevant fields.
     * @param url This is the URL that is calling the initialize method
     * @param rb The corresponding resource bundle that the application is using
     */    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Integer i = 0;
        for (Appointment appt : (ObservableList<Appointment>)DataHandler.readAppointments()) {
            i++;
        }
        SummaryItem appointments = new SummaryItem("Appointments", i);
        allSummaryItems.add(appointments);
        
        i = 0;
        for (Contact contact : (ObservableList<Contact>)DataHandler.readContacts()) {
            i++;
        }
        SummaryItem contacts = new SummaryItem("Contacts", i);
        allSummaryItems.add(contacts);
                
        i = 0;
        for (Customer cust : (ObservableList<Customer>)DataHandler.readCustomers()) {
            i++;
        }
        SummaryItem customers = new SummaryItem("Customers", i);
        allSummaryItems.add(customers);
                     
        i = 0;
        for (User user : (ObservableList<User>)DataHandler.readUsers()) {
            i++;
        }
        SummaryItem users = new SummaryItem("Users", i);
        allSummaryItems.add(users);  
        
        i = 0;
        for (Country country : (ObservableList<Country>)DataHandler.readCountries()) {
            i++;
        }
        SummaryItem countries = new SummaryItem("Countries", i);
        allSummaryItems.add(countries);

        i = 0;
        for (FirstLevelDivision frstLvlDiv : DataHandler.allFirstLvlDivs) {
            i++;
        }
        SummaryItem firstLevelDivisions = new SummaryItem("First Level Divisions", i);
        allSummaryItems.add(firstLevelDivisions);
        
        summaryTableView.setItems(allSummaryItems);
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }    
    
}
