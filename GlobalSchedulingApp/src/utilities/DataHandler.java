/** A package to handle utility classes and methods that are used across the application. */
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
/** A class that does the primary handling of data transfer between the client and the database. */
public class DataHandler {
        /** The user currently logged into the application. */
        public static User currentUser = null;
        /** A Boolean to flag if appointments are to be added new or modified. */
        public static boolean addAppointment = false;
        /** The selected appointment that should be modified. */
        public static Appointment apptToModify = null;
        /** The selected Customer that should be modified. */
        public static Customer custToModify = null;
        /** The connection class to be made to the database. */
        public static Connection connection = null;
        /** All Appointment objects created by querying the database. */
        public static ObservableList<Appointment> allAppts = FXCollections.observableArrayList();
        /** All Contact objects created by querying the database. */
        public static ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        /** All Country objects created by querying the database. */
        public static ObservableList<Country> allCountries = FXCollections.observableArrayList();
        /** All Customer objects created by querying the database. */
        public static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        /** All FirstLevelDivision objects created by querying the database. */
        public static ObservableList<FirstLevelDivision> allFirstLvlDivs = FXCollections.observableArrayList();
        /** All User objects created by querying the database. */
        public static ObservableList<User> allUsers = FXCollections.observableArrayList();
        /** A time formatter to parse date/times to human readable form. */
        public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        /** The query string that is to be utilized in a SQL statement. */
        private static String query;
        /** The prepared statement that is to be sent to the database. */
        private static PreparedStatement prepStmt;
        /** The currently ZoneId of the user logged into the application.
         * Primarily for localization purposes
         */
        public static ZoneId currentZoneId;
        /** The ResourceBundle used for language localization. */
        public static ResourceBundle rb;
        
    /** A static class to read all appointments in the database.
     * @throws SQLException Handles exceptions when using SQL queries to the database
     */
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
    /** A static class to handle creating and inserting new appointments into the database.
     * @param title The title of the new appointment
     * @param description The description of the new appointment
     * @param location The location of the new appointment
     * @param type The type of the new appointment
     * @param startTime The start date and time of the new appointment
     * @param endTime The ending date and time of the new appointment
     * @param createDate The date and time that the new appointment was created
     * @param createdBy The user that created the new appointment
     * @param lastUpdate The date and time that the appointment was last updated
     * @param lastUpdateBy The user that last updated the appointment
     * @param customerId The customer ID associated with the new appointment
     * @param userId The user ID associated with the new appointment
     * @param contactId The contact ID associated with the new appointment
     * @throws SQLException Handles exceptions when using SQL queries to the database
     */
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
    /** A static method to read all appointments that have been queried from the database.
     * @return The list of all appointments in the database
     */
    public static ObservableList readAppointments() {
        return allAppts;
    }
    /** A static method to modify and update a given appointment in the database.
     * @param id The ID of the appointment that is to be modified
     * @param title The title of the appointment to be modified
     * @param description The description of the appointment to be modified
     * @param location The location of the appointment to be modified
     * @param type The type of the appointment to be modified
     * @param startTime The start date and time of the appointment to be modified
     * @param endTime The ending date and time of the appointment to be modified
     * @param lastUpdate The date and time that the appointment was last updated
     * @param lastUpdateBy The user that last updated the appointment
     * @param customerId The customer ID associated with the appointment to be modified
     * @param userId The user ID associated with the appointment to be modified
     * @param contactId The contact ID associated with the appointment to be modified
     * @throws SQLException Handles exceptions when using SQL queries to the database
     */
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
    /** A static method to delete a selected appointment from the database.
     * @param id The ID of the appointment to be deleted
     * @throws SQLException Handles exceptions when using SQL queries to the database
     */
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
    
    /** A static method to read and create all appointment objects from the database.
     * @throws SQLException Handles exceptions when using SQL queries to the database
     */
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
    /** A static method to create a new contact object and add it to the database.
     * @param name The name of the contact to be created
     * @param email The email address of the contact to be created
     * @throws SQLException Handles exceptions when using SQL queries to the database
     */
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
    /** A static method that reads all of the contact records in the database.
     * @return The list of all contacts in the database
     */
    public static ObservableList readContacts() {
        return allContacts;
    }
    /** A static method to update a selected contact in the database.
     * @param id The ID of the contact to be modified
     * @param name The new name of the contact to be modified
     * @param email The new email of the contact to be modified
     * @throws SQLException Handles exceptions when using SQL queries to the database
     */
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
    /** A static method to delete a selected contact from the database.
     * @param id The ID of the contact to be deleted
     * @throws SQLException Handles exceptions when using SQL queries to the database
     */
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
    
    /** A static method that reads and creates all country objects from the database records.
     * @throws SQLException Handles exceptions when using SQL queries to the database
     */
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
    /** A static method that retrieves all country objects that have been created.
     * @return A list of all the available country objects
     */
    public static ObservableList readCountries() {
        return allCountries;
    }
    /** A static method that reads and creates all customer objects from database records.
     * @throws SQLException Handles exceptions when using SQL queries to the database
     */
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
    /** A static method that creates new customer objects and inserts their records into the database.
     * @param name The name of the new customer entry
     * @param address The address of the new customer entry
     * @param postalCode The postal code of the new customer entry
     * @param phoneNum The phone number of the new customer entry
     * @param createDate The date and time the new customer entry was created
     * @param createdBy The user who created the new customer entry
     * @param lastUpdate The date and time the customer entry was updated
     * @param lastUpdateBy The user who modified the customer entry
     * @param divId The first level division of customer entry
     * @throws SQLException Handles exceptions when using SQL queries to the database
     */
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
    /** A static method that retrieves all the customer objects that have been created.
     * @return A list of all customer objects within the database
     */
    public static ObservableList readCustomers() {
        return allCustomers;
    }
    /** A static method that modifies and updates a selected customer's entry in the database.
     * @param id The ID of the customer to be modified
     * @param name The name of the customer to be modified
     * @param address The address of the customer to be modified
     * @param postalCode The postal code of the customer to be modified
     * @param phoneNum The phone number of the customer to be modified
     * @param lastUpdate The date and time the customer entry was updated
     * @param lastUpdateBy The user who modified the customer entry
     * @param divId The first level division of customer entry
     * @throws SQLException Handles exceptions when using SQL queries to the database
     */
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
    /** A static method that deletes a selected customer from the database.
     * @param id The ID of the customer to be deleted
     * @throws SQLException Handles exceptions when using SQL queries to the database
     */
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
    /** A static method that reads and creates all users from records in the database.
     * @throws SQLException Handles exceptions when using SQL queries to the database
     */
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
    /** A static method that creates a new user object and inserts their record into the database.
     * @param name The name of the new user entry
     * @param password The password of the new user entry
     * @param createDate The date and time the new user entry was created
     * @param createdBy The user that created the new user entry
     * @param lastUpdate The date and time the user entry was last modified
     * @param lastUpdateBy The user that modified the user entry
     * @throws SQLException Handles exceptions when using SQL queries to the database
     */
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
    /** A static method that retrieves all user objects that have been created.
     * @return A list of all user objects that exist within the databse
     */
    public static ObservableList readUsers() {
        return allUsers;
    }
    /** A static method that modifies and updates a selected user's database entry.
     * @param id The ID of the user to modify
     * @param name The name of the new user entry
     * @param password The password of the new user entry
     * @param createDate The date and time the new user entry was created
     * @param createdBy The user that created the new user entry
     * @param lastUpdate The date and time the user entry was last modified
     * @param lastUpdateBy The user that modified the user entry
     * @throws SQLException Handles exceptions when using SQL queries to the database
     */
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
    /** A static method that deletes a selected user object and its entry in the database.
     * @param id The ID of the user to delete
     * @throws SQLException Handles exceptions when using SQL queries to the database
     */
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
    /** A static method to ensure that only the maximum ID is used when created a new appointment or customer.
     * @param modelType The data model of which to find the maximum ID for to populate the ID text fields in the GUI
     * @return The maximum ID integer value
     * @throws SQLException Handles exceptions when using SQL queries to the database
     */
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