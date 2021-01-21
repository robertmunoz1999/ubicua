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
        Cliente c = new Cliente();//inicializamos cliente
        String estadisticas=c.consultarCuboGrafica(cube);//obtenemos las estadisticas
        String arr[] = estadisticas.split("],");//almacenamos las estadisticas en un array
        DefaultTableModel miModelo = null;//ponemos a null temporalmente el defaulttablemodel
        try{
            //declaramos las columnas del cubo, para mostrarlos en la tabla
            String titulos [] = {"Id cubo", "Referencia data", "Capacidad", "C02", "Metano", "Humo", "Sello temporal", "Temperatura"};
            miModelo = new DefaultTableModel(null, titulos);//los añadimos a la tabla
            for (int i = 0; i < arr.length; i++) {//añadimos las filas
                miModelo.addRow(arr[i].split(", "));
            }
            //System.out.println(miModelo.getDataVector());
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return miModelo;//devolvemos el defaultTableModel
    }
}
