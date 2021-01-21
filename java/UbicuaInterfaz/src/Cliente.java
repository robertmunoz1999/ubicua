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

    private String HOST = "127.0.0.1"; //local
    private int PUERTO = 5001;//declaramos puerto
    private DataInputStream in;//declaramos in
    private DataOutputStream out;//declaramos out


    public void cuentaRegistro(String email, String password) {//metodo en el que registramos un nuevo usuario
        String mensaje = "";//declaramos mensaje
        try {
            //mensaje con la consulta, que ejecutará el servidor en la base de datos
            mensaje = "INSERT INTO account(\"email\",\"password\") VALUES("+"'"+email+""+"'"+",'"+password+"'"+") ";

            Socket sc = new Socket(HOST, PUERTO);//declaramos socket
            //server
            in = new DataInputStream(sc.getInputStream());//declaramos in
            out = new DataOutputStream(sc.getOutputStream());//declaramos out
            out.writeUTF(mensaje);//enviamos mensaje al servidor
            
            sc.close();//cerramos socket
            
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public String cuentaLogin(String email, String password) {//metodo por el que comprobamos el email y contraseña de un usuario
        String mensaje = "";//declaramos mensaje
        String respuesta="";//declaramos respuesta
        try {
            //ponemos como mensaje la consulta necesaria que tendrá que generar el servidor
            mensaje = "SELECT email,password FROM account WHERE email = " + "'" + email + "'" + " AND password = " + "'" + password + "'" + "";
            //inicializamos socket con el host y el puerto
            Socket sc = new Socket(HOST, PUERTO);
            //server
            in = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());
            out.writeUTF(mensaje);
            respuesta=in.readUTF();//se lee el mensaje que envia el servidor y se almacena

            sc.close();//se cierra el socket
            
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;//devolvemos el mensaje del servidor

    }
    public String consultar(Integer id){//metodo para ver caracteristicas del cubo
        String mensaje = "SELECT * FROM cube WHERE id = " + "'" + id + "'"  + "";//mensaje para el servidor
        String respuesta="";//declaramos respuesta
        try {
            Socket sc = new Socket(HOST, PUERTO);//declaramos socket
            //server
            in = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());
            out.writeUTF(mensaje);//enviamos mensaje al server
            respuesta = in.readUTF();//obtenemos respuesta del server
            sc.close();//cerramos socket
            
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return respuesta;//devolvemos la respuesta del server
    }
    public String consultarCubos(){////metodo para mostrar el id de los cubos ordenados(se usa en la lista de CubosBasura)
        String mensaje = "SELECT id FROM cube ORDER BY id ASC";//declaramos el mensaje que mandaremos al server
        String respuesta="";//declaramos respuesta
        try {
            Socket sc = new Socket(HOST, PUERTO);//declaramos el socket
            //server
            in = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());
            out.writeUTF(mensaje);//mandamos el mensaje al servidor
            respuesta = in.readUTF();//recibimos la respuesta del servidor
            sc.close();//cerrar socket
            
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return respuesta;
    }    
    public String consultarCuboGrafica(Integer id){//metodo para consultar el historial de datos del cubo
        String mensaje = "select * from cube_data_record where id_cube = " + id;//declaramos el mensaje que ejecutara el servidor en la bd
        String respuesta="";//declaramos respuesta
        try {
            Socket sc = new Socket(HOST, PUERTO);//declaramos socket
            //servidor
            in = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());
            out.writeUTF(mensaje);//enviamos al servidor el mensaje
            respuesta = in.readUTF();//leemos la respuesta del servidor
            sc.close();//cerramos socket
            
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return respuesta;//devolvemos la respuesta del servidor
    }
    public String consultarCubosActivos(){//para ver y dibujar en el mapa solo los cubos activos
        String mensaje = "SELECT * FROM cube WHERE deleted = false ORDER BY id ASC" ;
        String respuesta="";
        try {
            Socket sc = new Socket(HOST, PUERTO);
            //servidor
            in = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());
            out.writeUTF(mensaje);//enviamos al servidor el mensaje
            respuesta = in.readUTF();//leemos la respuesta del servidor
            sc.close();//cerramos
            
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return respuesta;
    }
}
