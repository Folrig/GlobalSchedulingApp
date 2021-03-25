/** The package of all of the data models and their methods used in the application. */
package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import utilities.DataHandler;

/**
 *
 * @author James Spencer
 */
/** The main class that acts as the entry point of the application. */
public class GlobalSchedulingApp extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }
    
    /** Main entry method for the scheduling application.
     * 
     * @param args the command line arguments
     * @throws SQLException Handles exceptions when using SQL queries to the database
     */

    public static void main(String[] args) throws SQLException {

        if (Locale.getDefault().toString().contains("fr")) {
            DataHandler.rb = ResourceBundle.getBundle("resources/Nat", Locale.getDefault());
        }
        Connection conn = DatabaseConnection.connectionInit();
        DataHandler.connection = conn;
        DataHandler.setAllCountries();
        DataHandler.setAllAppointments();
        DataHandler.setAllUsers();
        DataHandler.setAllContacts();
        DataHandler.setAllCustomers();
        
        launch(args);
        DatabaseConnection.connectionTerminate();
    }
}
