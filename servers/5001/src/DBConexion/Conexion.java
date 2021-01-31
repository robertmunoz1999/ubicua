/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBConexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
/**
 *
 * @author robertomunoz
 */

public class Conexion {
    private final Logger logger = Logger.getLogger(Conexion.class.getName());
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
    public void disconnect(){
        try {
            connection.close();
            System.out.println("Desconectado de BD!!");
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "No se ha podido cerrar la conexión",ex);
        } catch (Exception ex){
            logger.log(Level.WARNING, "Excepción capturada",ex);
        }
    }
}

