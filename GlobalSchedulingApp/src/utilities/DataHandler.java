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
import java.time.format.DateTimeFormatter;

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
        public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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
            LocalDateTime startTime = results.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTime = results.getTimestamp("End").toLocalDateTime();
            LocalDateTime createDate = results.getTimestamp("Create_Date").toLocalDateTime();
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
    
    public static void createAppointment(String title, String description, String location, String type, 
            LocalDateTime startTime, LocalDateTime endTime, LocalDateTime createDate, String createdBy, 
            LocalDateTime lastUpdate, String lastUpdateBy, int customerId, int userId, int contactId) throws SQLException {
        query = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Create_Date, "
                + "Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Query.setPreparedStatement(connection, query);
        prepStmt = Query.getPreparedStatement();
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
        prepStmt.execute();
        
        query = "SELECT Appointment_ID FROM appointments WHERE title = ? AND description = ?";
        Query.setPreparedStatement(connection, query);
        prepStmt = Query.getPreparedStatement();
        prepStmt.setString(1, title);
        prepStmt.setString(2, description);
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
    
    public static ObservableList readAppointments() {
        return allAppts;
    }
    
    public static void updateAppointment(int id, String title, String description, String location, String type, 
            LocalDateTime startTime, LocalDateTime endTime, LocalDateTime createDate, String createdBy, 
            LocalDateTime lastUpdate, String lastUpdateBy, int customerId, int userId, int contactId) throws SQLException {
        
        // Update entry in database
        query = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, "
                + "End = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, "
                + "Customer_ID = ?, User_ID = ?, Contact_ID = ?) WHERE Appointment_ID = ?";
        Query.setPreparedStatement(connection, query);
        prepStmt = Query.getPreparedStatement();
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
        prepStmt.setString(14, String.valueOf(id));
        prepStmt.execute();
        
        final int tempId = id;
        // Find entry in observable list, delete it, and add a new one to replace it
        if (id != 0) {
            allAppts.removeIf(appointment -> appointment.getId() == tempId);
            Appointment appt = new Appointment(id, title, description, location, type, startTime, endTime, createDate,
                createdBy, lastUpdate, lastUpdateBy, customerId, userId, contactId);
            allAppts.add(appt);
        }
    }
}
