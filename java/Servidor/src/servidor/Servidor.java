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
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {
    public static Conexion con = new Conexion();

    public static void main(String[] args) {

        ServerSocket servidor = null;
        Socket sc = null;
        DataInputStream in;
        DataOutputStream out;
        

        int PUERTO = 5001;

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
                if (firstWord.toLowerCase().contains("select")) {
                    String sitio = arr[3];
                    if(sitio.toLowerCase().contains("account")){
                        String login=consultarUsuario(mensaje);
                        out.writeUTF(login);
                    }
                    else if(sitio.toLowerCase().contains("cube")){
                        String infoCubo=consultarCubo(mensaje);
                        out.writeUTF(infoCubo);//devolvemos los datos al cliente
                    }else if(sitio.toLowerCase().contains("cube_data_record")){
                        String infoCuboEstadisticas=consultarGraficas(mensaje);
                        out.writeUTF(infoCuboEstadisticas);
                    }
                }
                else if (firstWord.toLowerCase().contains("insert")) {
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
        String encontrado="f";
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
                    encontrado="t";

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
    public static String consultarCubo(String mensaje){
        String salida="";
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
                    salida=rs.getString(2).trim()+","+rs.getString(3).trim()+","+rs.getString(4).trim()+","+rs.getString(5).trim();
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
    public static String consultarGraficas(String mensaje){
        String dts [] = new String[7];
        try{
            PreparedStatement pst = con.getConnection().prepareStatement(mensaje);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                dts[0] = rs.getString("id_cube");
                dts[1] = rs.getString("id");
                dts[2] = rs.getString("capacity");
                dts[3] = rs.getString("c02");
                dts[4] = rs.getString("methane");
                dts[5] = rs.getString("smoke");
                dts[6] = rs.getString("data_timestamp");
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }
        String s=dts[0]+","+dts[1]+","+dts[2]+","+dts[3]+","+dts[4]+","+dts[5]+","+dts[6];
        System.out.println(s);
        return s;
    }

}
