/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficos;

import DBConexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author robertomunoz
 */
public class Graficar {
    
    Connection connection = Conexion.getConnection();
    
    public DefaultTableModel mostrarCuentas(){
        DefaultTableModel miModelo = null;
        try{
            String titulos [] = {"Id", "Nombre"};
            String dts [] = new String[2];
            miModelo = new DefaultTableModel(null, titulos);
            String sql = "SELECT * FROM ACCOUNT";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                dts[0] = rs.getString("email");
                dts[1] = rs.getString("password");
                miModelo.addRow(dts);
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return miModelo;
        
    }
    
}
