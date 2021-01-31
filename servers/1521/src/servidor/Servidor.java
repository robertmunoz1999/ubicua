/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

/**
 *
 * @author abdalah
 */
import DBConexion.Conexion;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class Servidor {

    public static Conexion con = new Conexion();

    public static void main(String[] args) {

        ServerSocket servidor = null;
        Socket sc = null;
        DataInputStream in;
        DataOutputStream out;

        int PUERTO = 1521;

        try {
            servidor = new ServerSocket(PUERTO);
            System.out.println("Iniciado,esperando peticiones");

            while (true) {

                sc = servidor.accept();

                System.out.println("Conectado al servidor");
                in = new DataInputStream(sc.getInputStream());
                out = new DataOutputStream(sc.getOutputStream());

                String mensaje = in.readUTF();

                System.out.println(mensaje);

                //out.writeUTF("Mensaje prueba enviado por el servidor");
                String arr[] = mensaje.split(" ");
                
                String firstWord = arr[0];
                //System.out.println(firstWord);
                if (firstWord.toLowerCase().equals("select")) {
                    String sitio = arr[3];
                    if (sitio.toLowerCase().equals("account")) {
                        String login = consultarUsuario(mensaje);
                        out.writeUTF(login);
                    }else if(arr[1].equals("count")){
                            String cubos = NumCubos(mensaje);
                            out.writeUTF(cubos);
                        } else if (sitio.toLowerCase().equals("cube")) {
                        if(arr[1].equals("*")){
                            if(arr[5].equals("id")){
                                String infoCubo = consultarCubo(mensaje);
                                out.writeUTF(infoCubo);//devolvemos los datos al cliente
                            }
                            else if(arr[5].equals("deleted")){
                                String infoCubosActivos = consultarActivos(mensaje);
                                out.writeUTF(infoCubosActivos);//devolvemos los datos al cliente
                            }
                        }
                        else if(arr[1].equals("id")){
                            String cubos = Cubos(mensaje);
                            out.writeUTF(cubos);
                        }
                        else if(arr[1].equals("total_capacity")){
                            String cubos = CubosCapacidad(mensaje);
                            out.writeUTF(cubos);
                        }
                        
                        
                    } else if (sitio.toLowerCase().equals("cube_data_record")) {
                        if(arr[1].equals("capacity")){
                            String capacidadActual = CuboCapacidadActual(mensaje);
                            out.writeUTF(capacidadActual);
                        }else{
                           String infoCuboEstadisticas = getCubeData(mensaje);
                            out.writeUTF(infoCuboEstadisticas); 
                        }  
                    }
                } else if (firstWord.toLowerCase().equals("insert")) {
                    insertarUsuario(mensaje);
                }
                sc.close();
                System.out.println("Desconectado");

            }

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void insertarUsuario(String mensaje) {

        try {

            Statement stmt = con.getConnection().createStatement();
            stmt.executeUpdate(mensaje);
            //con.disconnect();
        } catch (SQLException ex) {
            System.out.println("error catch insert");
        }

    }

    public static String consultarUsuario(String mensaje) {
        String encontrado = "f";
        try {
            //Conexion con = new Conexion();
            ResultSet rs = null;
            Statement stmt = con.getConnection().createStatement();
            rs = stmt.executeQuery(mensaje);
            boolean centinela = false;
            while (rs.next()) {
                if (rs.wasNull() == false) {
                    centinela = true;
                    System.out.println("usuario encontrado");
                    encontrado = "t";

                } else {
                    System.out.println("no hay usuario");

                }

            }

            if (centinela == false) {
                System.out.println("no se ha encontrado el usuario");
            }
            //con.disconnect();
        } catch (SQLException ex) {
            System.out.println("error catch consultar usuario");
        }
        return encontrado;

    }

    public static String consultarCubo(String mensaje) {
        String salida = "";
        try {
            // TODO add your handling code here:
            //Conexion con = new Conexion();
            ResultSet rs = null;
            Statement stmt = con.getConnection().createStatement();
            rs = stmt.executeQuery(mensaje);
            boolean centinela = false;
            while (rs.next()) {
                //System.out.println(rs.getString(1).trim()+"---"+rs.getString(2).trim()+"---"+rs.getString(3).trim()+"---"+rs.getString(4).trim());
                if (rs.wasNull() == false) {
                    centinela = true;
                    System.out.println(" encontrado");
                    salida = rs.getString(2).trim() + "," + rs.getString(3).trim() + "," + rs.getString(4).trim() + "," + rs.getString(5).trim();
                    System.out.println(salida);
                    //devuelve una salida tipo string de la consulta, separada por comas

                } else {
                    System.out.println("no ");

                }

            }
            if (centinela == false) {
                System.out.println("no se ha encontrado ");
            }
            //con.disconnect();
        } catch (SQLException ex) {
            System.out.println("error catch consultar cubo");
        }
        return salida;
    }


    public static String getCubeData(String mensaje) {
        DefaultTableModel miModelo = null;
        try {
            String titulos[] = {"Id cubo", "Referencia data", "Capacidad", "CO2", "Metano", "Humo", "Sello temporal", "Temperatura"};
            String dts[] = new String[9];

            miModelo = new DefaultTableModel(null, titulos);

            PreparedStatement pst = con.getConnection().prepareStatement(mensaje);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                dts[0] = rs.getString("id_cube");
                dts[1] = rs.getString("id");
                dts[2] = rs.getString("capacity");
                dts[3] = rs.getString("co2");
                dts[4] = rs.getString("methane");
                dts[5] = rs.getString("smoke");
                dts[6] = rs.getString("data_timestamp");
                dts[7] = rs.getString("temperature");
                miModelo.addRow(dts);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //System.out.println("vectores del modelo: " + miModelo.getDataVector());
        String s = miModelo.getDataVector().toString();
        return s;
    }
    
    public static String Cubos(String mensaje){
        String salida = "";
        try {
            // TODO add your handling code here:
            ResultSet rs = null;
            Statement stmt = con.getConnection().createStatement();
            rs = stmt.executeQuery(mensaje);
            boolean centinela = false;
            while (rs.next()) {
                if (rs.wasNull() == false) {
                    centinela = true;
                    salida = salida + rs.getString("id") + " ";


                } else {
                    System.out.println("no ");

                }

            }
            if (centinela == false) {
                System.out.println("no se ha encontrado ");
            }
            //con.disconnect();
        } catch (SQLException ex) {
            System.out.println("error catch cubos");
        }
        return salida;
        
        
    }
    public static String CubosCapacidad(String mensaje){///////////////////////////////////////////
        String salida = "";
        try {
            // TODO add your handling code here:
            ResultSet rs = null;
            Statement stmt = con.getConnection().createStatement();
            rs = stmt.executeQuery(mensaje);
            boolean centinela = false;
            while (rs.next()) {
                if (rs.wasNull() == false) {
                    centinela = true;
                    salida = salida + rs.getString("total_capacity")+" ";
                } else {
                    System.out.println("no ");
                }
            }
            if (centinela == false) {
                System.out.println("no se ha encontrado ");
            }
            //con.disconnect();
        } catch (SQLException ex) {
            System.out.println("error catch cubos");
        }
        return salida;
        
        
    }
    public static String NumCubos(String mensaje){///////////////////////////////////////////
        String salida = "";
        try {
            // TODO add your handling code here:
            ResultSet rs = null;
            Statement stmt = con.getConnection().createStatement();
            rs = stmt.executeQuery(mensaje);
            boolean centinela = false;
            while (rs.next()) {
                if (rs.wasNull() == false) {
                    centinela = true;
                    salida = rs.getString(1).trim();
                } else {
                    System.out.println("no ");
                }
            }
            if (centinela == false) {
                System.out.println("no se ha encontrado ");
            }
            //con.disconnect();
        } catch (SQLException ex) {
            System.out.println("error catch cubos");
        }
        return salida;
    }
    public static String CuboCapacidadActual(String mensaje){///////////////////////////////////////////
        String salida = "";
        try {
            // TODO add your handling code here:
            ResultSet rs = null;
            Statement stmt = con.getConnection().createStatement();
            rs = stmt.executeQuery(mensaje);
            boolean centinela = false;
            while (rs.next()) {
                if (rs.wasNull() == false) {
                    centinela = true;
                    salida = rs.getString(1).trim();
                } else {
                    System.out.println("no ");
                }
            }
            if (centinela == false) {
                System.out.println("no se ha encontrado ");
            }
            //con.disconnect();
        } catch (SQLException ex) {
            System.out.println("error catch cubos");
        }
        return salida;
    }
    public static String consultarActivos(String mensaje) {
        String salida = "";
        try {
            ResultSet rs = null;
            Statement stmt = con.getConnection().createStatement();
            rs = stmt.executeQuery(mensaje);
            boolean centinela = false;
            while (rs.next()) {
                if (rs.wasNull() == false) {
                    centinela = true;
                    System.out.println(" encontrado");
                    salida = salida + rs.getString(1).trim() + "," + rs.getString(3).trim() + "," + rs.getString(4).trim() + "," + rs.getString(6).trim() + "," + rs.getString(7).trim() + "<";
                } else {
                    System.out.println("no ");
                }
            }
            if (centinela == false) {
                System.out.println("no se ha encontrado ");
            }
        } catch (SQLException ex) {
            System.out.println("error catch consultar cubo");
        }
        return salida;//devuelve una salida tipo string de la consulta, separada por comas
    }
}
