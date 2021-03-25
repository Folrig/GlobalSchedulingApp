/** A package to handle utility classes and methods that are used across the application. */
package utilities;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

/**
 *
 * @author James Spencer
 */
/** A utility class to handle SQL queries with the database connection. */
public class Query {
    // Statement reference
    /** A PreparedStatement object that will be used to hold SQL statements. */
    private static PreparedStatement statement;
    
    // Create Statement Object
    /** A static method to set the PreparedStatement to the given string.
     * @param connection The Connection object being used to connect to the database
     * @param query The string holding the SQL statement to be run
     * @throws SQLException Handles exceptions when using SQL queries to the database
     */
    public static void setPreparedStatement(Connection connection, String query) throws SQLException {
        statement = connection.prepareStatement(query);
    }
    
    /** A static method to retrieve the PreparedStatement for use running SQL statements.
     * @return The PreparedStatement to be run in the database
     */
    public static PreparedStatement getPreparedStatement() {
        return statement;
    }
}
