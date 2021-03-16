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
public class CustModViewController implements Initializable {
    Stage stage;
    Parent scene;
    ObservableList<Country> countries = FXCollections.observableArrayList();
    
    @FXML private Button addCustButton;
    @FXML private Button updateCustButton;
    @FXML private Button deleteCustButton;
    @FXML private TableView<Customer> custInfoTableView;
    @FXML private TableColumn<Customer, Integer> custIdColumn;
    @FXML private TableColumn<Customer, String> custNameColumn;
    @FXML private TableColumn<Customer, String> addressColumn;
    @FXML private TableColumn<Customer, Integer> postalCodeColumn;
    @FXML private TableColumn<Customer, String> frstLvlDivColumn;
    @FXML private TableColumn<Customer, String> countryColumn;
    @FXML private TableColumn<Customer, String> phoneColumn;
    @FXML private TableColumn<Customer, String> createDateColumn;
    @FXML private TableColumn<Customer, String> createByColumn;
    @FXML private TableColumn<Customer, LocalDateTime> lastUpdateColumn;
    @FXML private TableColumn<Customer, String> lastUpdateByColumn;
    @FXML private TextField custIdTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField postalCodeTextField;
    @FXML private ComboBox<Country> countryComboBox;
    @FXML private ComboBox<FirstLevelDivision> firstLvlDivComboBox;
    @FXML private TextField custNameTextField;
    @FXML private TextField phoneNumTextField;
    @FXML private Button backToMainButton;

    @FXML
    void onAddCustBtnClicked(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("Add New Customer");
        scene = FXMLLoader.load(getClass().getResource("/view/CustAddView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.centerOnScreen();
    }

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

    @FXML
    void onCntryCmbBoxValueChange(ActionEvent event) {
        firstLvlDivComboBox.setItems(countryComboBox.getValue().getAllAssociatedDivisions());
        firstLvlDivComboBox.getSelectionModel().select(0);
    }

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
     * @param url
     * @param rb
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
