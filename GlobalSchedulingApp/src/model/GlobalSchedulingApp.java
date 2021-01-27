/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.DatabaseConnection;
import utilities.Query;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
     
        Connection connection = DatabaseConnection.connectionInit();
        DataHandler.connection = connection;
        DataHandler.setAllAppointments();
        
        // DataHandler.addAppointment("Hello", "there", "General" "Kenobi", "00:10:10".toL, STYLESHEET_CASPIAN, STYLESHEET_CASPIAN, STYLESHEET_MODENA, LocalTime.MIN, LocalTime.MIN, LocalDate.MAX, STYLESHEET_MODENA, LocalDateTime.MAX, STYLESHEET_MODENA, 0, 0, 0);
        
        launch(args);
        DatabaseConnection.connectionTerminate();
    }
}
