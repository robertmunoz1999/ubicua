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
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class Servidor {
 
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
 
                System.out.println("Conectado");
                in = new DataInputStream(sc.getInputStream());
                out = new DataOutputStream(sc.getOutputStream());
 
                String mensaje = in.readUTF();
 
                System.out.println(mensaje);
 
                out.writeUTF("Mensaje prueba enviado por el servidor");
 
                sc.close();
                System.out.println("Desconectado");
 
            }
 
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    }
 
}