/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.stage.Stage;
import model.Country;
import model.FirstLevelDivision;

/**
 * FXML Controller class
 *
 * @author James Spencer
 */
public class CustModViewController implements Initializable {
    Stage stage;
    Parent scene;
    
    @FXML private Button addCustButton;
    @FXML private Button updateCustButton;
    @FXML private Button deleteCustButton;
    @FXML private TableView<?> custInfoTableView;
    @FXML private TableColumn<?, ?> custIdColumn;
    @FXML private TableColumn<?, ?> custNameColumn;
    @FXML private TableColumn<?, ?> addressColumn;
    @FXML private TableColumn<?, ?> postalCodeColumn;
    @FXML private TableColumn<?, ?> frstLvlDivColumn;
    @FXML private TableColumn<?, ?> countryColumn;
    @FXML private TableColumn<?, ?> phoneColumn;
    @FXML private TableColumn<?, ?> createDateColumn;
    @FXML private TableColumn<?, ?> createByColumn;
    @FXML private TableColumn<?, ?> lastUpdateColumn;
    @FXML private TableColumn<?, ?> lastUpdateByColumn;
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
    void onDeleteCustBtnClicked(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete Customer?");
        alert.setHeaderText("Delete this customer?");
        alert.setContentText("OK to delete customer" + 
            "\n" + "Cancel to go back to modifying customer");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // delete customer
            
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setTitle("Main Menu");
            scene = FXMLLoader.load(getClass().getResource("/view/MainWindowView.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            stage.centerOnScreen();
        }
    }

    @FXML
    void onUpdateCustBtnClicked(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Modifying Customer?");
        alert.setHeaderText("Return to main menu?");
        alert.setContentText("OK to return to main menu" + 
            "\n" + "Cancel to go back to modifying customer");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // update customer
            
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setTitle("Main Menu");
            scene = FXMLLoader.load(getClass().getResource("/view/MainWindowView.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            stage.centerOnScreen();
        }
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
