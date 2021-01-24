/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author James Spencer
 */
public class Query {
    // Statement reference
    private static Statement statement;
    
    // Create Statement Object
    public static void setStatement(Connection connection) throws SQLException {
        statement = connection.createStatement();
    }
    
    public static Statement getStatement() {
        return statement;
    }
}
