/** The package that controls changes and I/O in the GUI. */
package controller;

import java.io.*;
import java.net.URL;
import java.time.*;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import utilities.DataHandler;

/**
 * FXML Controller class
 *
 * @author James Spencer
 */
/** This class controls the GUI for logging into the main loop of the application. */
public class LoginController implements Initializable {
    /** The stage that will be used to set up the next GUI scene. */
    Stage stage;
    /** The GUI window that will be shown upon exiting the customer add interface. */
    Parent scene;
    /** The file writer that will update the login text file. */
    FileWriter fWriter;
    /** The print writer that will append the text to the login text file. */
    PrintWriter pWriter;
    /** The string that stores the path name of the login history text file. */
    String fileName = "login_activity.txt";
    
    /** The label that will display the title in the correct language for the current locale. */
    @FXML private Label titleLabel;
    /** The label that will display the login text field in the correct language for the current locale. */    
    @FXML private Label loginLabel;
    /** The label that will mark the user name text field in the correct language for the current locale. */    
    @FXML private Label usernameLabel;
    /** The label that will mark the password text field in the correct language for the current locale. */    
    @FXML private Label passwordLabel;
    /** The text field used to capture the user's username input for logging in. */
    @FXML private TextField usernameTextField;
    /** The text field used to capture the user's password input for logging in. */
    @FXML private TextField passwordTextField;
    /** The label that will mark the changing current location label. */
    @FXML private Label currentLocationLabel;
    /** The label that will change to show the user's current location based upon system default. */
    @FXML private Label locationLabel;
    /** The button clicked to attempt logging into the program using the input credentials. */
    @FXML private Button loginBtn;
    /** The button clicked to exit the program. */
    @FXML private Button exitBtn;
    /** The label that will mark the changing current zone label. */
    @FXML private Label zoneLabel;
    /** The label that will change to show the user's current zone based upon system default. */
    @FXML private Label currentZoneIdLabel;
    
    /** This method handles when the button is clicked to exit and close the program.
     * @param event This is the mouse button click event that calls the method
     */    
    @FXML
    void onExitBtnClicked(ActionEvent event) {
        System.exit(0);
    }

    /** This method handles when the button is clicked to attempt logging in to the main menu using the input credentials.
     * @param event This is the mouse button click event that calls the method
     * @throws IOException Handles exceptions between I/O devices
     */    
    @FXML
    void onLoginBtnClicked(ActionEvent event) throws IOException {
        fWriter = new FileWriter(fileName, true);
        pWriter = new PrintWriter(fWriter);
        String usernameInput = usernameTextField.getText();
        String passwordInput = passwordTextField.getText();
        if (!usernameInput.equals("test") || !passwordInput.equals("test")) {
            pWriter.println("** LOGIN ATTEMPT **   Result: INVALID  Date: " + LocalDate.now(ZoneOffset.UTC) +
                    "  Time: " + LocalTime.now(ZoneOffset.UTC) + "  Username: " + usernameInput + 
                    "  Password: " + passwordInput);
            pWriter.close();
            showWrongInfoBox();
        }
        else {
            for (User user : (ObservableList<User>)DataHandler.readUsers()) {
                if (usernameInput.equals(user.getName())) {
                    DataHandler.currentUser = user;
                    break;
                }
            }
            pWriter.println("** LOGIN ATTEMPT **   Result: VALID  Date: " + LocalDate.now(ZoneOffset.UTC) +
                    "  Time: " + LocalTime.now(ZoneOffset.UTC) + "  Username: " + usernameInput + 
                    "  Password: " + passwordInput);
            pWriter.close();
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            if (Locale.getDefault().getLanguage().equals("fr")){
                stage.setTitle(DataHandler.rb.getString("menu") + " " + DataHandler.rb.getString("main"));
            } else {
                stage.setTitle("Main Menu");
            }
            scene = FXMLLoader.load(getClass().getResource("/view/MainWindowView.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            stage.centerOnScreen();
        }
    }
    
    /** This method displays an alert when the username and password combination are incorrect. */
    private void showWrongInfoBox (){
        Alert alert = new Alert(AlertType.WARNING);
        if (Locale.getDefault().getLanguage().equals("fr")){
            alert.setTitle(DataHandler.rb.getString("incorrect") + " " + DataHandler.rb.getString("username") +
                    " " + DataHandler.rb.getString("or") + " " + DataHandler.rb.getString("password"));
            alert.setHeaderText(DataHandler.rb.getString("the") + " " + DataHandler.rb.getString("username") +
                    " " + DataHandler.rb.getString("or") + " " + DataHandler.rb.getString("password") +
                    " " + DataHandler.rb.getString("is") + " " + DataHandler.rb.getString("incorrect"));
        } else {
            alert.setTitle("Incorrect Username Or Password");
            alert.setHeaderText("The username or password is incorrect.");
        }
        alert.showAndWait();
    }
    
    /** This method handles the GUI is first loaded and populates all relevant fields.
     * @param url This is the URL that is calling the initialize method
     * @param rb The corresponding resource bundle that the application is using
     */    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DataHandler.currentZoneId = ZoneId.systemDefault();
        Locale locale = Locale.getDefault();
        if (locale.toString().contains("en")) {
            currentLocationLabel.setText("United States");
            currentZoneIdLabel.setText(DataHandler.currentZoneId.toString());
        }
        if (Locale.getDefault().getLanguage().equals("fr")) {
            titleLabel.setText(DataHandler.rb.getString("global") + " " + DataHandler.rb.getString("scheduling"));
            loginLabel.setText(DataHandler.rb.getString("login"));
            usernameLabel.setText(DataHandler.rb.getString("username"));
            passwordLabel.setText(DataHandler.rb.getString("password"));
            loginBtn.setText(DataHandler.rb.getString("login"));
            exitBtn.setText(DataHandler.rb.getString("exit"));
            locationLabel.setText(DataHandler.rb.getString("current") + " " + DataHandler.rb.getString("location") + ":");
            currentLocationLabel.setText("La France");
            zoneLabel.setText(DataHandler.rb.getString("current") + " " + DataHandler.rb.getString("zone") + ":");
            currentZoneIdLabel.setText(DataHandler.currentZoneId.toString());
        }
    }    
}
