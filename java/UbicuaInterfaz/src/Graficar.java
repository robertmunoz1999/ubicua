/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author robertomunoz
 */
public class Graficar {
    
    
    public DefaultTableModel getCubeData(Integer cube){
        Cliente c = new Cliente();
        String estadisticas=c.consultarCuboGrafica(cube);
        String arr[] = estadisticas.split(",");
        DefaultTableModel miModelo = null;
        try{
            String titulos [] = {"Id cubo", "Referencia data", "Capacidad", "C02", "Metano", "Humo", "Sello temporal"};
            miModelo = new DefaultTableModel(null, titulos);
            miModelo.addRow(arr);
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return miModelo;
    }
}
