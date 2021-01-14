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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author James Spencer
 */
public class MainWindowViewController implements Initializable {
    
    @FXML
    private TableView<?> scheduleTableView;

    @FXML
    private TableColumn<?, ?> apptIdColumn;

    @FXML
    private TableColumn<?, ?> titleColumn;

    @FXML
    private TableColumn<?, ?> descriptionColumn;

    @FXML
    private TableColumn<?, ?> locationColumn;

    @FXML
    private TableColumn<?, ?> contactColumn;

    @FXML
    private TableColumn<?, ?> typeColumn;

    @FXML
    private TableColumn<?, ?> startColumn;

    @FXML
    private TableColumn<?, ?> endColumn;

    @FXML
    private TableColumn<?, ?> custIdColumn;

    @FXML
    private Button apptAudButton;

    @FXML
    private Button custAudButton;

    @FXML
    private RadioButton weeklyViewRadioButton;

    @FXML
    private ToggleGroup viewRadioToggleGroup;

    @FXML
    private RadioButton monthlyViewRadioButton;

    @FXML
    private Button reportOneButton;

    @FXML
    private Button reportTwoButton;

    @FXML
    private Button reportThreeButton;

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