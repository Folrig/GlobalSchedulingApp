/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class GlobalSchedulingApp extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * @param args the command line arguments
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
