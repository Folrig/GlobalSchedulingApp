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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author James Spencer
 */
public class CustModViewController implements Initializable {
    
    @FXML
    private Button addCustButton;

    @FXML
    private Button updateCustButton;

    @FXML
    private Button deleteCustButton;

    @FXML
    private TableView<?> custInfoTableView;

    @FXML
    private TableColumn<?, ?> custIdColumn;

    @FXML
    private TableColumn<?, ?> custNameColumn;

    @FXML
    private TableColumn<?, ?> addressColumn;

    @FXML
    private TableColumn<?, ?> postalCodeColumn;

    @FXML
    private TableColumn<?, ?> frstLvlDivColumn;

    @FXML
    private TableColumn<?, ?> countryColumn;

    @FXML
    private TableColumn<?, ?> phoneColumn;

    @FXML
    private TableColumn<?, ?> createDateColumn;

    @FXML
    private TableColumn<?, ?> createByColumn;

    @FXML
    private TableColumn<?, ?> lastUpdateColumn;

    @FXML
    private TableColumn<?, ?> lastUpdateByColumn;

    @FXML
    private TextField custIdTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField postalCodeTextField;

    @FXML
    private ComboBox<?> countryComboBox;

    @FXML
    private ComboBox<?> firstLvlDivComboBox;

    @FXML
    private TextField custNameTextField;

    @FXML
    private TextField phoneNumTextField;

    @FXML
    private Button backToMainButton;

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
