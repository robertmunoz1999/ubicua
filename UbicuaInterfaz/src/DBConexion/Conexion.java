/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBConexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author robertomunoz
 */
public class Conexion {
    
    static Connection connection = null;
    public static Connection getConnection(){
        if(connection == null){
            try{
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection("jdbc:postgresql://3.137.154.117:5432/postgres", "postgres", "12345");
            } catch (ClassNotFoundException | SQLException ex){
               ex.printStackTrace();
            }
        }
        return connection;
    }
    
}

