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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 *
 * @author James Spencer
 */
// POTENTIAL TODO: USERS MIGHT HAVE APPOINTMENTS, MIGHT NOT BE NECESSARY FOR ASSESSMENT!
public class DataHandler {
        public static User currentUser = null;
        public static boolean addAppointment = false;
        public static Appointment apptToModify = null;
        public static Customer custToModify = null;
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
        public static ZoneId currentZoneId;
        public static ResourceBundle rb;
        
        
    public static void setAllAppointments() throws SQLException {
        allAppts.clear();
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
        query = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Create_Date, " +
                "Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
        prepStmt.setInt(11, customerId);
        prepStmt.setInt(12, userId);
        prepStmt.setInt(13, contactId);
        prepStmt.execute();
        
        query = "SELECT Appointment_ID FROM appointments WHERE Title = ? AND Description = ?";
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
            System.out.println("Problem in createAppointment!");
        }
    }
    public static ObservableList readAppointments() {
        return allAppts;
    }
    // UNTESTED, possible issue with lambda
    public static void updateAppointment(int id, String title, String description, String location, String type, 
            LocalDateTime startTime, LocalDateTime endTime,
            LocalDateTime lastUpdate, String lastUpdateBy, int customerId, int userId, int contactId) throws SQLException {
        
        // Update entry in database
        query = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, " +
                "End = ?, Last_Update = ?, Last_Updated_By = ?, " +
                "Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        Query.setPreparedStatement(connection, query);
        prepStmt = Query.getPreparedStatement();
        prepStmt.setString(1, title);
        prepStmt.setString(2, description);
        prepStmt.setString(3, location);
        prepStmt.setString(4, type);
        prepStmt.setString(5, startTime.toString());
        prepStmt.setString(6, endTime.toString());
        prepStmt.setString(7, lastUpdate.toString());
        prepStmt.setString(8, lastUpdateBy);
        prepStmt.setInt(9, customerId);
        prepStmt.setInt(10, userId);
        prepStmt.setInt(11, contactId);
        prepStmt.setInt(12, id);
        System.out.println(prepStmt.toString());
        prepStmt.execute();
        
        if (id != 0) {
            DataHandler.setAllAppointments();
        }
    }
    // UNTESTED
    public static void deleteAppointment(int id) throws SQLException {
        query = "DELETE FROM appointments WHERE Appointment_ID = ?";
        Query.setPreparedStatement(connection, query);
        prepStmt = Query.getPreparedStatement();
        prepStmt.setInt(1, id);
        prepStmt.execute();

        if (id != 0) {
            DataHandler.setAllAppointments();
        }
    }
    
    // UNTESTED
    public static void setAllContacts() throws SQLException {
        allContacts.clear();
        query = "SELECT * FROM contacts";
        Query.setPreparedStatement(connection, query);
        prepStmt = Query.getPreparedStatement();
        prepStmt.execute();
        ResultSet results = prepStmt.getResultSet();

        while(results.next()) {
            int id = results.getInt("Contact_ID");
            String name = results.getString("Contact_Name");
            String email = results.getString("Email");

            Contact contact = new Contact(id, name, email);
            allContacts.add(contact);      
        }
    }
    // UNTESTED
    public static void createContact(String name, String email) throws SQLException {
        if (!email.contains("@")) {
            System.out.println("Email in wrong format!");
            return;
        }
        query = "INSERT INTO contacts(Contact_Name, Email)" +
                "VALUES (?, ?)";
        Query.setPreparedStatement(connection, query);
        prepStmt = Query.getPreparedStatement();
        prepStmt.setString(1, name);
        prepStmt.setString(2, email);
        prepStmt.execute();

        query = "SELECT Contact_ID FROM contacts WHERE Contact_Name = ? AND Email = ?";
        Query.setPreparedStatement(connection, query);
        prepStmt = Query.getPreparedStatement();
        prepStmt.setString(1, name);
        prepStmt.setString(2, email);
        prepStmt.execute();
        ResultSet results = prepStmt.getResultSet();
        int id = 0;
        while(results.next()) {
            id = results.getInt("Contact_ID");
        }
        if (id != 0) {
            Contact contact = new Contact(id, name, email);
            allContacts.add(contact);
        } else {
            System.out.println("Problem in createContact");
        }
    }
    public static ObservableList readContacts() {
        return allContacts;
    }
    // UNTESTED, possible issue with lambda
    public static void updateContact(int id, String name, String email) throws SQLException {
        // Update entry in database
        query = "UPDATE contacts SET Contact_Name = ?, Email = ? WHERE Contact_ID = ?";
        Query.setPreparedStatement(connection, query);
        prepStmt = Query.getPreparedStatement();
        prepStmt.setString(1, name);
        prepStmt.setString(2, email);
        prepStmt.setInt(3, id);
        prepStmt.execute();

        if (id != 0) {
            DataHandler.setAllContacts();
        }
    }
    // UNTESTED
    public static void deleteContact(int id) throws SQLException {
        query = "DELETE FROM contacts WHERE Contact_ID = ?";
        Query.setPreparedStatement(connection, query);
        prepStmt = Query.getPreparedStatement();
        prepStmt.setInt(1, id);
        prepStmt.execute();

        final int tempId = id;
        if (id != 0) {
            allContacts.removeIf(contact -> contact.getId() == tempId);
        }
    }
    
    // UNTESTED
    public static void setAllCountries() throws SQLException {
        ObservableList<FirstLevelDivision> usaDivList = FXCollections.observableArrayList();
        ObservableList<FirstLevelDivision> ukDivList = FXCollections.observableArrayList();
        ObservableList<FirstLevelDivision> canadaDivList = FXCollections.observableArrayList();
        for (int i = 1; i <= 3; i++) {
            query = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID = ?";
            Query.setPreparedStatement(connection, query);
            prepStmt = Query.getPreparedStatement();
            prepStmt.setString(1, String.valueOf(i));
            prepStmt.execute();
            ResultSet results = prepStmt.getResultSet();
            
            while(results.next()) {
                int divId = results.getInt("Division_ID");
                String name = results.getString("Division");
                LocalDateTime createDate = results.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = results.getString("Created_By");
                LocalDateTime lastUpdate = results.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdateBy = results.getString("Last_Updated_By");
                int countryId = results.getInt("COUNTRY_ID");                
                FirstLevelDivision div = new FirstLevelDivision(divId, name, createDate, createdBy,
                    lastUpdate, lastUpdateBy, countryId);
                allFirstLvlDivs.add(div);
                if (countryId == 1) {
                    usaDivList.add(div);
                } else if (countryId == 2) {
                    ukDivList.add(div);
                } else if (countryId == 3) {
                    canadaDivList.add(div);
                }
            }
        }
        
        query = "SELECT * FROM countries";
        Query.setPreparedStatement(connection, query);
        prepStmt = Query.getPreparedStatement();
        prepStmt.execute();
        ResultSet results = prepStmt.getResultSet();
      
        while(results.next()) {
            int countryId = results.getInt("Country_ID");
            String name = results.getString("Country");
            LocalDateTime createDate = results.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = results.getString("Created_By");
            LocalDateTime lastUpdate = results.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdateBy = results.getString("Last_Updated_By");
            Country country = null;
            if (countryId == 1) {
                country = new Country(countryId, name, createDate, createdBy, lastUpdate,
                    lastUpdateBy, usaDivList);
            } else if (countryId == 2) {
                country = new Country(countryId, name, createDate, createdBy, lastUpdate,
                    lastUpdateBy, ukDivList);
            } else if (countryId == 3) {
                country = new Country(countryId, name, createDate, createdBy, lastUpdate,
                    lastUpdateBy, canadaDivList);
            }
            if (country != null) {
                allCountries.add(country);
            }
        }
    }
    public static ObservableList readCountries() {
        return allCountries;
    }
    // UNTESTED
    public static void setAllCustomers() throws SQLException {
        allCustomers.clear();
        query = "SELECT * FROM customers";
        Query.setPreparedStatement(connection, query);
        prepStmt = Query.getPreparedStatement();
        prepStmt.execute();
        ResultSet results = prepStmt.getResultSet();

        while(results.next()) {
            int id = results.getInt("Customer_ID");
            String name = results.getString("Customer_Name");
            String address = results.getString("Address");
            String postalCode = results.getString("Postal_Code");
            String phoneNum = results.getString("Phone");
            LocalDateTime createDate = results.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = results.getString("Created_By");
            LocalDateTime lastUpdate = results.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdateBy = results.getString("Last_Updated_By");
            int divId = results.getInt("Division_ID");
            
            Customer newCust = new Customer(id, name, address, postalCode, phoneNum, createDate,
                createdBy, lastUpdate, lastUpdateBy, divId);
            allCustomers.add(newCust);      
        }
    }
    // UNTESTED
    public static void createCustomer(String name, String address, String postalCode, String phoneNum,
            LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdateBy,
            int divId) throws SQLException {
        query = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Create_Date, " +
                "Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Query.setPreparedStatement(connection, query);
        prepStmt = Query.getPreparedStatement();
        prepStmt.setString(1, name);
        prepStmt.setString(2, address);
        prepStmt.setString(3, postalCode);
        prepStmt.setString(4, phoneNum);
        prepStmt.setString(5, createDate.toString());
        prepStmt.setString(6, createdBy);
        prepStmt.setString(7, lastUpdate.toString());
        prepStmt.setString(8, lastUpdateBy);
        prepStmt.setInt(9, divId);
        prepStmt.execute();

        query = "SELECT Customer_ID FROM customers WHERE Customer_Name = ? AND Address = ?";
        Query.setPreparedStatement(connection, query);
        prepStmt = Query.getPreparedStatement();
        prepStmt.setString(1, name);
        prepStmt.setString(2, address);
        prepStmt.execute();
        ResultSet results = prepStmt.getResultSet();
        int id = 0;
        while(results.next()) {
            id = results.getInt("Customer_ID");
        }
        if (id != 0) {
            Customer newCust = new Customer(id, name, address, postalCode, phoneNum, createDate,
                createdBy, lastUpdate, lastUpdateBy, divId);
            allCustomers.add(newCust);
        } else {
            System.out.println("Problem in createCustomer");
        }
    }
    public static ObservableList readCustomers() {
        return allCustomers;
    }
    // UNTESTED
    public static void updateCustomer(int id, String name, String address, String postalCode,
            String phoneNum, LocalDateTime lastUpdate, String lastUpdateBy,
            int divId) throws SQLException {
        // Update entry in database
        query = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, " +
                "Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? " +
                "WHERE Customer_ID = ?";
        Query.setPreparedStatement(connection, query);
        prepStmt = Query.getPreparedStatement();
        prepStmt.setString(1, name);
        prepStmt.setString(2, address);
        prepStmt.setString(3, postalCode);
        prepStmt.setString(4, phoneNum);
        prepStmt.setString(5, lastUpdate.toString());
        prepStmt.setString(6, lastUpdateBy);
        prepStmt.setInt(7, divId);
        prepStmt.setInt(8, id);
        prepStmt.execute();

        if (id != 0) {
            DataHandler.setAllCustomers();
        }
    }
    // UNTESTED
    public static void deleteCustomer(int id) throws SQLException {
        query = "DELETE FROM appointments WHERE Customer_ID = ?";
        Query.setPreparedStatement(connection, query);
        prepStmt = Query.getPreparedStatement();
        prepStmt.setInt(1, id);
        prepStmt.execute();
        
        query = "DELETE FROM customers WHERE Customer_ID = ?";
        Query.setPreparedStatement(connection, query);
        prepStmt = Query.getPreparedStatement();
        prepStmt.setInt(1, id);
        prepStmt.execute();

        if (id != 0) {
            DataHandler.setAllAppointments();
            DataHandler.setAllCustomers();
        }
    }
    // UNTESTED
    public static void setAllUsers() throws SQLException {
        allUsers.clear();
        query = "SELECT * FROM users";
        Query.setPreparedStatement(connection, query);
        prepStmt = Query.getPreparedStatement();
        prepStmt.execute();
        ResultSet results = prepStmt.getResultSet();
        
        while(results.next()) {
            int id = results.getInt("User_ID");
            String name = results.getString("User_Name");
            String password = results.getString("Password");
            LocalDateTime createDate = results.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = results.getString("Created_By");
            LocalDateTime lastUpdate = results.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdateBy = results.getString("Last_Updated_By");

            User user = new User(id, name, password, createDate, createdBy, lastUpdate, lastUpdateBy);
            allUsers.add(user);
        }
    }
    // UNTESTED
    public static void createUser(String name, String password, LocalDateTime createDate,
            String createdBy, LocalDateTime lastUpdate, String lastUpdateBy) throws SQLException {
        query = "INSERT INTO users(User_Name, Password, Create_Date, Create_By, Last_Update, Last_Updated_by) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        Query.setPreparedStatement(connection, query);
        prepStmt = Query.getPreparedStatement();
        prepStmt.setString(1, name);
        prepStmt.setString(2, password);
        prepStmt.setString(3, createDate.toString());
        prepStmt.setString(4, createdBy);
        prepStmt.setString(5, lastUpdate.toString());
        prepStmt.setString(6, lastUpdateBy);
        prepStmt.execute();

        query = "SELECT User_ID FROM users WHERE User_Name = ? AND Password = ?";
        Query.setPreparedStatement(connection, query);
        prepStmt = Query.getPreparedStatement();
        prepStmt.setString(1, name);
        prepStmt.setString(2, password);
        prepStmt.execute();
        ResultSet results = prepStmt.getResultSet();
        int id = 0;
        while(results.next()) {
            id = results.getInt("User_ID");
        }
        if (id != 0) {
            User user = new User(id, name, password, createDate, createdBy, lastUpdate, lastUpdateBy);
            allUsers.add(user);
        } else {
            System.out.println("Problem in createUser");
        }
    }
    public static ObservableList readUsers() {
        return allUsers;
    }
    // UNTESTED, possible issue with lambda
    public static void updateUser(int id, String name, String password, LocalDateTime createDate,
            String createdBy, LocalDateTime lastUpdate, String lastUpdateBy) throws SQLException {
        // Update entry in database
        query = "UPDATE users SET User_Name = ?, Password = ?, Create_Date = ?, Created_By = ?, " +
                "Last_Update = ?, Last_Updated_By = ? WHERE Contact_ID = ?";
        Query.setPreparedStatement(connection, query);
        prepStmt = Query.getPreparedStatement();
        prepStmt.setString(1, name);
        prepStmt.setString(2, password);
        prepStmt.setString(3, createDate.toString());
        prepStmt.setString(4, createdBy);
        prepStmt.setString(5, lastUpdate.toString());
        prepStmt.setString(6, lastUpdateBy);
        prepStmt.setInt(7, id);
        prepStmt.execute();

        final int tempId = id;
        // Find entry in observable list, delete it, and add a new one to replace it
        if (id != 0) {
            allUsers.removeIf(user -> user.getId() == tempId);
            User user = new User(id, name, password, createDate, createdBy, lastUpdate, lastUpdateBy);
            allUsers.add(user);
        }
    }
    // UNTESTED
    public static void deleteUser(int id) throws SQLException {
        query = "DELETE FROM users WHERE User_ID = ?";
        Query.setPreparedStatement(connection, query);
        prepStmt = Query.getPreparedStatement();
        prepStmt.setString(1, String.valueOf(id));
        prepStmt.execute();

        if (id != 0) {
            DataHandler.setAllUsers();
        }
    }
    
    public static int getNextIdValue(String modelType) throws SQLException {
        int nextId = 0;
        if (modelType == "Appointment") {
            query = "SELECT MAX(Appointment_ID) FROM appointments";
            Query.setPreparedStatement(connection, query);
            prepStmt = Query.getPreparedStatement();
            prepStmt.execute();
            ResultSet results = prepStmt.getResultSet();
            
            while(results.next()) {
                nextId = results.getInt("MAX(Appointment_ID)") + 1;
            }
        } else if (modelType == "Customer") {
            query = "SELECT MAX(Customer_ID) FROM customers";
            Query.setPreparedStatement(connection, query);
            prepStmt = Query.getPreparedStatement();
            prepStmt.execute();
            ResultSet results = prepStmt.getResultSet();
            
            while(results.next()) {
                nextId = results.getInt("MAX(Customer_ID)") + 1;
            }
        }
        
        return nextId;
    }
}