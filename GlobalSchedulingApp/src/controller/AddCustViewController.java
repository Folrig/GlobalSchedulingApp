/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author James Spencer
 */
public class AddCustViewController implements Initializable {

    @FXML
    private TextField custIdTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField postalCodeTextField;

    @FXML
    private TextField custNameTextField;

    @FXML
    private ComboBox<?> countryComboBox;

    @FXML
    private TextField phoneNumTextField;

    @FXML
    private ComboBox<?> firstLvlDivComboBox;

    @FXML
    private Button addCustButton;

    @FXML
    private Button cancelButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
