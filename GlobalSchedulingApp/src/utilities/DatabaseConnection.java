/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author James Spencer
 */
public class DatabaseConnection {
    // Java Database Connection string parts
    private static final String PROTOCOL = "jdbc";
    private static final String VENDOR = ":mysql:";
    private static final String IP_ADDRESS = "//wgudb.ucertify.com/WJ0763q";
    
    // Full Java database connection URL
    private static final String JDBC_URL = PROTOCOL + VENDOR + IP_ADDRESS;
    
    // Reference to driver and connection interfaces
    private static final String MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static Connection conn = null;
    
    // User name to enter database
    private static final String USERNAME = "U0763q";
    
    // Password for database
    private static final String PASSWORD = "53688951019";
    
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
