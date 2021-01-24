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
        ObservableList<Appointment> allAppts = FXCollections.observableArrayList();
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        ObservableList<FirstLevelDivision> allFirstLvlDivs = FXCollections.observableArrayList();
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        
        Connection connection = DatabaseConnection.connectionInit();
        Query.setStatement(connection);
        Statement statement = Query.getStatement();
        
        
        
        /*
        String insertStatement = "";
        statement.execute(insertStatement);
        if (statement.getUpdateCount() > 0) {
            System.out.println(statement.getUpdateCount() + " row(s) affect!");
        }
        else {
            System.out.println("No changes in database.");
        }
        */
        
        
        
        launch(args);
        DatabaseConnection.connectionTerminate();
    }
}
