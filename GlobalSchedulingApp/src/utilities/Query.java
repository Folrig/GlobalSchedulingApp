/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

/**
 *
 * @author James Spencer
 */
public class Query {
    // Statement reference
    private static PreparedStatement statement;
    
    // Create Statement Object
    public static void setPreparedStatement(Connection connection, String query) throws SQLException {
        statement = connection.prepareStatement(query);
    }
    
    public static PreparedStatement getPreparedStatement() {
        return statement;
    }
}
