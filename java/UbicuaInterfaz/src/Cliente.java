/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author abdalah
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {

    private String HOST = "127.0.0.1"; //prueba en local
    private int PUERTO = 5001;
    private DataInputStream in;
    private DataOutputStream out;


    public void cuentaRegistro(String email, String password) {
        String mensaje = "";
        try {
            mensaje = "INSERT INTO account(\"email\",\"password\") VALUES("+"'"+email+""+"'"+",'"+password+"'"+") ";

            Socket sc = new Socket(HOST, PUERTO);
            in = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());
            out.writeUTF(mensaje);
            sc.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public String cuentaLogin(String email, String password) {
        String mensaje = "";
        String respuesta="";
        try {
            mensaje = "SELECT email,password FROM account WHERE email = " + "'" + email + "'" + " AND password = " + "'" + password + "'" + "";
            
            Socket sc = new Socket(HOST, PUERTO);
            in = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());
            out.writeUTF(mensaje);
            respuesta=in.readUTF();

            System.out.println(respuesta);
            sc.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;

    }
    public String consultar(Integer id){
        String mensaje = "SELECT * FROM cube WHERE id = " + "'" + id + "'"  + "";
        String respuesta="";
        try {
            Socket sc = new Socket(HOST, PUERTO);
            in = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());
            out.writeUTF(mensaje);
            respuesta = in.readUTF();
            sc.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return respuesta;
    }
    public String consultarCubos(){
        String mensaje = "SELECT id FROM cube ORDER BY id ASC";
        String respuesta="";
        try {
            Socket sc = new Socket(HOST, PUERTO);
            in = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());
            out.writeUTF(mensaje);
            respuesta = in.readUTF();
            sc.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return respuesta;
    }    
    public String consultarCuboGrafica(Integer id){
        String mensaje = "select * from cube_data_record where id_cube = " + id;
        String respuesta="";
        try {
            Socket sc = new Socket(HOST, PUERTO);
            in = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());
            out.writeUTF(mensaje);
            respuesta = in.readUTF();
            sc.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return respuesta;
    }
}
