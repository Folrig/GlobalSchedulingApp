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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author James Spencer
 */
public class ApptModViewController implements Initializable {
    
    @FXML
    private Label apptModLabel;

    @FXML
    private TextField locationTextField;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private ComboBox<?> startHourComboBox;

    @FXML
    private ComboBox<?> startMinuteComboBox;

    @FXML
    private ComboBox<?> startAmPmComboBox;

    @FXML
    private ComboBox<?> userIdComboBox;

    @FXML
    private TextField apptIdTextField;

    @FXML
    private ComboBox<?> contactComboBox;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ComboBox<?> endHourComboBox;

    @FXML
    private ComboBox<?> endMinuteComboBox;

    @FXML
    private ComboBox<?> endAmPmComboBox;

    @FXML
    private ComboBox<?> custIdComboBox;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField typeTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button addCustButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
