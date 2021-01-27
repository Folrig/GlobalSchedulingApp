/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Contact;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;
import model.User;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 *
 * @author James Spencer
 */
public class DataHandler {
        public static Connection connection = null;
        public static ObservableList<Appointment> allAppts = FXCollections.observableArrayList();
        public static ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        public static ObservableList<Country> allCountries = FXCollections.observableArrayList();
        public static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        public static ObservableList<FirstLevelDivision> allFirstLvlDivs = FXCollections.observableArrayList();
        public static ObservableList<User> allUsers = FXCollections.observableArrayList();
        private static String query;
        private static PreparedStatement prepStmt;
        
    public static void setAllAppointments() throws SQLException {
        query = "SELECT * FROM appointments";
        Query.setPreparedStatement(connection, query);
        prepStmt = Query.getPreparedStatement();
        prepStmt.execute();
        ResultSet results = prepStmt.getResultSet();
        
        while(results.next()) {
            int id = results.getInt("Appointment_ID");
            String title = results.getString("Title");
            String descrip = results.getString("Description");
            String location = results.getString("Location");
            String type = results.getString("Type");
            LocalTime startTime = results.getTime("Start").toLocalTime();
            LocalTime endTime = results.getTime("End").toLocalTime();
            LocalDate createDate = results.getDate("Create_Date").toLocalDate();
            String createdBy = results.getString("Created_By");
            LocalDateTime lastUpdate = results.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdateBy = results.getString("Last_Updated_By");
            int custId = results.getInt("Customer_ID");
            int userId = results.getInt("User_ID");
            int contactId = results.getInt("Contact_ID");
            
            Appointment appt = new Appointment(id, title, descrip, location, type, startTime, endTime, createDate,
                createdBy, lastUpdate, lastUpdateBy, custId, userId, contactId);
            allAppts.add(appt);
        }
    }
    
    public static void addAppointment(String title, String description, String location, String type, 
            LocalTime startTime, LocalTime endTime, LocalDate createDate, String createdBy, 
            LocalDateTime lastUpdate, String lastUpdateBy, int customerId, int userId, int contactId) throws SQLException {
        query = "INSERT INTO appoinments(Title, Description, Location, Type, Start, End, Created_Date,"
                + "Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES"
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        prepStmt.setString(1, title);
        prepStmt.setString(2, description);
        prepStmt.setString(3, location);
        prepStmt.setString(4, type);
        prepStmt.setString(5, startTime.toString());
        prepStmt.setString(6, endTime.toString());
        prepStmt.setString(7, createDate.toString());
        prepStmt.setString(8, createdBy);
        prepStmt.setString(9, lastUpdate.toString());
        prepStmt.setString(10, lastUpdateBy);
        prepStmt.setString(11, String.valueOf(customerId));
        prepStmt.setString(12, String.valueOf(userId));
        prepStmt.setString(13, String.valueOf(contactId));
        prepStmt = Query.getPreparedStatement();
        prepStmt.execute();
        
        query = "SELECT id FROM appointments WHERE title = ? AND description = ?";
        prepStmt.setString(1, title);
        prepStmt.setString(2, description);
        prepStmt = Query.getPreparedStatement();
        prepStmt.execute();
        ResultSet results = prepStmt.getResultSet();
        int id = 0;
        while(results.next()) {
            id = results.getInt("Appointment_ID");
        }
        if (id != 0) {
            Appointment appt = new Appointment(id, title, description, location, type, startTime, endTime, createDate,
                createdBy, lastUpdate, lastUpdateBy, customerId, userId, contactId);
            allAppts.add(appt);
        } else {
            System.out.println("Problem in addAppointment!");
        }
    }
}
