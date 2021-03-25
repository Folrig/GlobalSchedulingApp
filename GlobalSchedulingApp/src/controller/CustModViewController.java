/** The package that controls changes and I/O in the GUI. */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;
import utilities.DataHandler;

/**
 * FXML Controller class
 *
 * @author James Spencer
 */
/** This class controls the GUI for modifying existing customers. */
public class CustModViewController implements Initializable {
    /** The stage that will be used to set up the next GUI scene. */
    Stage stage;
    /** The GUI window that will be shown upon exiting the customer add interface. */
    Parent scene;
    /** A list of all available countries that users, customers, and contacts can be from. */
    ObservableList<Country> countries = FXCollections.observableArrayList();
    
    /** The table view that displays all existing customer information. */
    @FXML private TableView<Customer> custInfoTableView;
    /** The table column that displays all existing customer ID values. */
    @FXML private TableColumn<Customer, Integer> custIdColumn;
    /** The table column that displays all existing customer name values. */
    @FXML private TableColumn<Customer, String> custNameColumn;
    /** The table column that displays all existing customer address values. */
    @FXML private TableColumn<Customer, String> addressColumn;
    /** The table column that displays all existing customer postal code values. */
    @FXML private TableColumn<Customer, Integer> postalCodeColumn;
    /** The table column that displays all existing customer first level division ID values. */
    @FXML private TableColumn<Customer, String> frstLvlDivColumn;
    /** The table column that displays all existing customer phone number values. */
    @FXML private TableColumn<Customer, String> phoneColumn;
    /** The table column that displays the dates the customer record was created. */
    @FXML private TableColumn<Customer, String> createDateColumn;
    /** The table column that displays the user that created the customer record. */
    @FXML private TableColumn<Customer, String> createByColumn;
    /** The table column that displays when the customer record was last updated. */
    @FXML private TableColumn<Customer, LocalDateTime> lastUpdateColumn;
    /** The table column that displays the user who last updated the customer record. */
    @FXML private TableColumn<Customer, String> lastUpdateByColumn;
    /** The text field that displays the selected customer ID value. */
    @FXML private TextField custIdTextField;
    /** The text field that displays and modifies the selected customer address. */
    @FXML private TextField addressTextField;
    /** The text field that displays and modifies the selected customer postal code. */
    @FXML private TextField postalCodeTextField;
    /** The combo box used to modify the existing customer country. */
    @FXML private ComboBox<Country> countryComboBox;
    /** The combo box used to modify the existing customer first level division. */
    @FXML private ComboBox<FirstLevelDivision> firstLvlDivComboBox;
    /** The text field that displays and modifies the selected customer name. */
    @FXML private TextField custNameTextField;
    /** The text field that displays and modifies the selected phone number. */
    @FXML private TextField phoneNumTextField;

    /** This method handles when the button is clicked to modify an existing a customer.
     * @param event This is the mouse button click event that calls the method
     * @throws IOException Handles exceptions between I/O devices
     */
    @FXML
    void onAddCustBtnClicked(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("Add New Customer");
        scene = FXMLLoader.load(getClass().getResource("/view/CustAddView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.centerOnScreen();
    }

    /** This method handles when the button is clicked to cancel modifying a customer and return to the main menu.
     * @param event This is the mouse button click event that calls the method
     * @throws IOException Handles exceptions between I/O devices
     */
    @FXML
    void onBackBtnClicked(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Modifying Customer?");
        alert.setHeaderText("Return to main menu?");
        alert.setContentText("OK to return to main menu" + 
            "\n" + "Cancel to go back to modifying customer");

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

    /** This method handles when the country combo box value is changed.
     * When the country is changed, the first level division combo box values are changed to correspond with the selected country.
     * @param event This is the mouse button click event that calls the method
     */
    @FXML
    void onCntryCmbBoxValueChange(ActionEvent event) {
        firstLvlDivComboBox.setItems(countryComboBox.getValue().getAllAssociatedDivisions());
        firstLvlDivComboBox.getSelectionModel().select(0);
    }

    /** This method handles when the button to delete a selected customer is clicked.
     * @param event This is the mouse button click event that calls the method
     * @throws IOException Handles exceptions between I/O devices
     * @throws SQLException Handles exceptions when using SQL queries to the database
     */
    @FXML
    void onDeleteCustBtnClicked(ActionEvent event) throws IOException, SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete Customer?");
        alert.setHeaderText("Delete customer " + custNameTextField.getText() + "?");
        alert.setContentText("OK to delete customer" + 
            "\n" + "Cancel to go back to modifying customer");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            DataHandler.deleteCustomer(Integer.valueOf(custIdTextField.getText()));
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setTitle("Main Menu");
            scene = FXMLLoader.load(getClass().getResource("/view/MainWindowView.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            stage.centerOnScreen();
        }
    }

    /** This method handles when the button to update the selected customer with new values is clicked.
     * @param event This is the mouse button click event that calls the method
     * @throws IOException Handles exceptions between I/O devices
     * @throws SQLException Handles exceptions when using SQL queries to the database
     */
    @FXML
    void onUpdateCustBtnClicked(ActionEvent event) throws IOException, SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Modifying Customer?");
        alert.setHeaderText("Return to main menu?");
        alert.setContentText("OK to return to main menu" + 
            "\n" + "Cancel to go back to modifying customer");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            int id = Integer.valueOf(custIdTextField.getText());
            String custName = custNameTextField.getText();
            String address = addressTextField.getText();
            String postalCode = postalCodeTextField.getText();
            String phoneNum = phoneNumTextField.getText();
            int firstLvlDiv = firstLvlDivComboBox.getValue().getId();
            
            if (!address.contains(" ") && !address.contains(",")) {
                Alert warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("Incorrect Address Format");
                warning.setHeaderText("Address Format Is Incorrect");
                warning.setContentText("Addresses must be in one of the following formats:\n\n" +
                        "US: 123 ABC Street, White Plains\n" +
                        "Canadian: 123 ABC Street, Newmarket\n" +
                        "UK: 123 ABC Street, Greenwich, London");

                warning.showAndWait();
            } else {
                DataHandler.updateCustomer(id, custName, address, postalCode, phoneNum,
                        LocalDateTime.now(), DataHandler.currentUser.getName(), firstLvlDiv);

                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                stage.setTitle("Main Menu");
                scene = FXMLLoader.load(getClass().getResource("/view/MainWindowView.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
                stage.centerOnScreen();
            }
        }
    }
    
    /** This method populates the text fields and combo boxes with the existing customer's current values.
     * @param event This is the mouse button click event that calls the method
     */
    @FXML
    void onRowClicked(MouseEvent event) {
        Customer cust = custInfoTableView.getSelectionModel().getSelectedItem();
        custIdTextField.setText(Integer.toString(cust.getId()));
        addressTextField.setText(cust.getAddress());
        postalCodeTextField.setText(cust.getPostalCode());
        custNameTextField.setText(cust.getName());
        phoneNumTextField.setText(cust.getPhoneNum());
        
        divisionLoop:
        for (FirstLevelDivision firstLvlDiv : DataHandler.allFirstLvlDivs) {
            if (firstLvlDiv.getId() == cust.getDivisionId()) {
                for (Country country : DataHandler.allCountries) {
                    if (country.getId() == firstLvlDiv.getCountryId()) {
                        countryComboBox.getSelectionModel().select(country);
                        firstLvlDivComboBox.setItems(country.getAllAssociatedDivisions());
                        firstLvlDivComboBox.getSelectionModel().select(firstLvlDiv);
                        break divisionLoop;
                    }
                }
            }
        }
    }
    
    /** This method populates the table view with existing customer data. */
    private void populateCustomerTableView() {
        custInfoTableView.setItems(DataHandler.readCustomers());
        custIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        custNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        frstLvlDivColumn.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        createDateColumn.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        createByColumn.setCellValueFactory(new PropertyValueFactory<>("createBy"));
        lastUpdateColumn.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        lastUpdateByColumn.setCellValueFactory(new PropertyValueFactory<>("lastUpdateBy"));
    }
    /**
     * Initializes the controller class.
     * @param url This is the URL that is calling the initialize method
     * @param rb The corresponding resource bundle that the application is using
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set up all GUI controls with base values
        try {
            DataHandler.setAllCustomers();
            countries = DataHandler.readCountries();
            populateCustomerTableView();
            countryComboBox.setItems(DataHandler.readCountries());
            countryComboBox.getSelectionModel().select(0);
            firstLvlDivComboBox.setItems(countries.get(0).getAllAssociatedDivisions());
            firstLvlDivComboBox.getSelectionModel().select(0);
        } catch (SQLException ex) {
            Logger.getLogger(ApptAddViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
