/** A package to handle utility classes and methods that are used across the application. */
package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author James Spencer
 */
/** A class to handle connecting to the database. */
public class DatabaseConnection {
    // Java Database Connection string parts
    /** The protocol used to connect to the database. */
    private static final String PROTOCOL = "jdbc";
    /** The vendor/platform the database is running on. */
    private static final String VENDOR = ":mysql:";
    /** The actual URL address of the database. */
    private static final String IP_ADDRESS = "//wgudb.ucertify.com/WJ0763q";
    
    // Full Java database connection URL
    /** A concatenation of the entire connecting string for connecting to the database. */
    private static final String JDBC_URL = PROTOCOL + VENDOR + IP_ADDRESS;
    
    // Reference to driver and connection interfaces
    /** The JDBC driver that is being used for interfacing with the database. */
    private static final String MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
    /** A reference to the actual connection object. */
    private static Connection conn = null;
    
    // User name to enter database
    /** The user name for database credentials. */
    private static final String USERNAME = "U0763q";
    
    // Password for database
    /** The password used as a credential to enter the database. */
    private static final String PASSWORD = "53688951019";
    
    /** A method to handle connecting to the database.
     * @return The Connection object to the database
     */
    public static Connection connectionInit() {
        try {
            Class.forName(MYSQL_JDBC_DRIVER);
            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            System.out.println("DatabaseConnection.connectionInit: Connected successfully");
        }
        catch(ClassNotFoundException | SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        
        return conn;
    }
    
    /** A method to handle terminating the database connection. */
    public static void connectionTerminate() {
        try {
            conn.close();
            System.out.println("DatabaseConnection.connectionTerminate: Connection closed");
        }
        catch(SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
