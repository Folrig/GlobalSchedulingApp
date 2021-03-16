/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class CustAddViewController implements Initializable {
    Stage stage;
    Parent scene;
    ObservableList<Country> countries = FXCollections.observableArrayList();
    
    @FXML private TextField custIdTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField postalCodeTextField;
    @FXML private TextField custNameTextField;
    @FXML private TextField phoneNumTextField;
    @FXML private ComboBox<Country> countryComboBox;
    @FXML private ComboBox<FirstLevelDivision> firstLvlDivComboBox;
    @FXML private Button addCustButton;
    @FXML private Button cancelButton;

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
    
    @FXML
    void onCntryCmbBoxValueChange(ActionEvent event) {
        firstLvlDivComboBox.setItems(countryComboBox.getValue().getAllAssociatedDivisions());
        firstLvlDivComboBox.getSelectionModel().select(0);
    }
    
    /**
     * Initializes the controller class.
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
