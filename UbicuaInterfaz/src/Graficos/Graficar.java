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
    
    public DefaultTableModel getCubeData(int cube){
        DefaultTableModel miModelo = null;
        try{
            String titulos [] = {"Id cubo", "Referencia data", "Capacidad", "C02", "Metano", "Humo", "Sello temporal"};
            String dts [] = new String[7];
            miModelo = new DefaultTableModel(null, titulos);
            String sql = "select * from cube_data_record where id_cube = " + cube;
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                dts[0] = rs.getString("id_cube");
                dts[1] = rs.getString("id");
                dts[2] = rs.getString("capacity");
                dts[3] = rs.getString("c02");
                dts[4] = rs.getString("methane");
                dts[5] = rs.getString("smoke");
                dts[6] = rs.getString("data_timestamp");
                miModelo.addRow(dts);
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return miModelo;
    }
}
