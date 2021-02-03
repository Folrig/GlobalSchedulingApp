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
        prepStmt.setString(11, String.valueOf(customerId));
        prepStmt.setString(12, String.valueOf(userId));
        prepStmt.setString(13, String.valueOf(contactId));
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
            for (Contact contact : allContacts) {
                if (contact.getId() == appt.getContactId()) {
                    contact.addAppointment(appt);
                }
            }
            for (Customer customer : allCustomers) {
                if (customer.getId() == appt.getCustomerId()) {
                    customer.addAppointment(appt);
                }
            }
        } else {
            System.out.println("Problem in createAppointment!");
        }
    }
    public static ObservableList readAppointments() {
        return allAppts;
    }
    // UNTESTED, possible issue with lambda
    public static void updateAppointment(int id, String title, String description, String location, String type, 
            LocalDateTime startTime, LocalDateTime endTime, LocalDateTime createDate, String createdBy, 
            LocalDateTime lastUpdate, String lastUpdateBy, int customerId, int userId, int contactId) throws SQLException {
        
        // Update entry in database
        query = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, " +
                "End = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, " +
                "Customer_ID = ?, User_ID = ?, Contact_ID = ?) WHERE Appointment_ID = ?";
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
            // for every contact in all contacts
            // if the updated appointment contact ID is the contact ID
            // remove it from their appointment observable list
            // This is an ugly n^2 but oh well
            // Possible error because of removing objects in a list while iterating
            for (Contact contact : allContacts) {
                for (Appointment appt : contact.getAllAppointments()) {
                    if (appt.getContactId() == contact.getId()) {
                        contact.deleteAppointment(appt);
                    }
                }
            }
            for (Customer customer : allCustomers) {
                for (Appointment appt : customer.getAllAppointments()) {
                    if (appt.getContactId() == customer.getId()) {
                        customer.deleteAppointment(appt);
                    }
                }
            }
            
            allAppts.removeIf(appointment -> appointment.getId() == tempId);
            Appointment newAppt = new Appointment(id, title, description, location, type, startTime, endTime, createDate,
                createdBy, lastUpdate, lastUpdateBy, customerId, userId, contactId);
            allAppts.add(newAppt);
            for (Contact contact : allContacts) {
                if (contact.getId() == newAppt.getContactId()) {
                    contact.addAppointment(newAppt);
                }
            }
            for (Customer customer : allCustomers) {
                if (customer.getId() == newAppt.getCustomerId()) {
                    customer.addAppointment(newAppt);
                }
            }
        }
    }
    // UNTESTED
    public static void deleteAppointment(int id) throws SQLException {
        query = "DELETE FROM appointments WHERE Appointment_ID = ?";
        Query.setPreparedStatement(connection, query);
        prepStmt = Query.getPreparedStatement();
        prepStmt.setString(1, String.valueOf(id));
        prepStmt.execute();
        
        final int tempId = id;
        if (id != 0) {
            allAppts.removeIf(appointment -> appointment.getId() == tempId);
        }
    }
    
    // UNTESTED
    public static void setAllContacts() throws SQLException {
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
            allAppts.stream().filter((appt) -> (appt.getContactId() == contact.getId())).forEachOrdered((appt) -> {
                contact.addAppointment(appt);
            });
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
        query = "UPDATE contacts SET name = ?, email = ? WHERE Contact_ID = ?";
        Query.setPreparedStatement(connection, query);
        prepStmt = Query.getPreparedStatement();
        prepStmt.setString(1, name);
        prepStmt.setString(2, email);
        prepStmt.setString(3, String.valueOf(id));
        prepStmt.execute();

        final int tempId = id;
        // Find entry in observable list, delete it, and add a new one to replace it
        if (id != 0) {
            allContacts.removeIf(contact -> contact.getId() == tempId);
            Contact contact = new Contact(id, name, email);
            allContacts.add(contact);
        }
    }
    // UNTESTED
    public static void deleteContact(int id) throws SQLException {
        query = "DELETE FROM contacts WHERE Contact_ID = ?";
        Query.setPreparedStatement(connection, query);
        prepStmt = Query.getPreparedStatement();
        prepStmt.setString(1, String.valueOf(id));
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
}