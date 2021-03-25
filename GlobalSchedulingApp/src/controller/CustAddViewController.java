/** The package that controls changes and I/O in the GUI. */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.FirstLevelDivision;
import utilities.DataHandler;

/**
 * FXML Controller class
 *
 * @author James Spencer
 */

/** This class controls the GUI for creating new customers. */
public class CustAddViewController implements Initializable {
    /** The stage that will be used to set up the next GUI scene. */
    Stage stage;
    /** The GUI window that will be shown upon exiting the customer add interface. */
    Parent scene;
    /** A list of all available countries that users, customers, and contacts can be from. */
    ObservableList<Country> countries = FXCollections.observableArrayList();
    
    /** The text field that displays that customer ID. */
    @FXML private TextField custIdTextField;
    /** The text field that displays that customer address. */
    @FXML private TextField addressTextField;
    /** The text field that displays that customer postal code. */
    @FXML private TextField postalCodeTextField;
    /** The text field that displays that customer name. */    
    @FXML private TextField custNameTextField;
    /** The text field that displays that customer phone number */
    @FXML private TextField phoneNumTextField;
    /** The combo box used to select the customer's country. */
    @FXML private ComboBox<Country> countryComboBox;
    /** The combo box used to select the customer's first level division. */
    @FXML private ComboBox<FirstLevelDivision> firstLvlDivComboBox;

    /** This method handles when the button is clicked to create a customer.
     * @throws IOException Handles exceptions between I/O devices
     * @throws SQLException Handles exceptions when using SQL queries to the database
     * @param event This is the mouse button click event that calls the method
     */
    @FXML
    void onAddCustBtnClicked(ActionEvent event) throws SQLException, IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Adding Customer?");
        alert.setHeaderText("Confirm Adding Customer");
        alert.setContentText("OK to Confirm" + 
            "\n" + "Cancel to go back to adding customer");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            String custName = custNameTextField.getText();
            String address = addressTextField.getText();
            String postalCode = postalCodeTextField.getText();
            String phoneNum = phoneNumTextField.getText();
            int firstLvlDiv = firstLvlDivComboBox.getValue().getId();
            
            if (!address.contains(" ") || !address.contains(",")) {
                Alert warning = new Alert(AlertType.WARNING);
                warning.setTitle("Incorrect Address Format");
                warning.setHeaderText("Address Format Is Incorrect");
                warning.setContentText("Addresses must be in one of the following formats:\n\n" +
                        "US: 123 ABC Street, White Plains\n" +
                        "Canadian: 123 ABC Street, Newmarket\n" +
                        "UK: 123 ABC Street, Greenwich, London");

                warning.showAndWait();
            } else {
                DataHandler.createCustomer(custName, address, postalCode, phoneNum, LocalDateTime.now(),
                        DataHandler.currentUser.getName(), LocalDateTime.now(), DataHandler.currentUser.getName(),
                        firstLvlDiv);

                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                stage.setTitle("Main Menu");
                scene = FXMLLoader.load(getClass().getResource("/view/MainWindowView.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
                stage.centerOnScreen();
            }
        }
    }

    /** This method handles when the button is clicked to cancel creating a customer and return to the main menu.
     * @param event This is the mouse button click event that calls the method
     * @throws IOException Handles exceptions between I/O devices
     */
    @FXML
    void onCancelBtnClicked(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Adding Customer?");
        alert.setHeaderText("Return to main menu?");
        alert.setContentText("OK to return to main menu" + 
            "\n" + "Cancel to go back to adding customer");

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
     When the country is changed, the first level division combo box values are changed to correspond with the selected country.
     @param event This is the mouse button click event that calls the method
     */
    @FXML
    void onCntryCmbBoxValueChange(ActionEvent event) {
        firstLvlDivComboBox.setItems(countryComboBox.getValue().getAllAssociatedDivisions());
        firstLvlDivComboBox.getSelectionModel().select(0);
    }
    
    /** This method handles the GUI is first loaded and populates all relevant fields.
     * @param url This is the URL that is calling the initialize method
     * @param rb The corresponding resource bundle that the application is using
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            countries = DataHandler.readCountries();
            custIdTextField.setText(Integer.toString(DataHandler.getNextIdValue("Customer")));
            countryComboBox.setItems(DataHandler.readCountries());
            countryComboBox.getSelectionModel().select(0);
            firstLvlDivComboBox.setItems(countries.get(0).getAllAssociatedDivisions());
            firstLvlDivComboBox.getSelectionModel().select(0);
        } catch (SQLException ex) {
            Logger.getLogger(CustAddViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
