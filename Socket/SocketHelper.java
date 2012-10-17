
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author raito
 */
public class SocketHelper {
    /*
     * Lee datos del socket. Supone que se le pasa un buffer con hueco 
     *	suficiente para los datos. Devuelve el numero de bytes leidos o
     * 0 si se cierra fichero o -1 si hay error.
     */
    public static String leeSocket(Socket p_sk) {
        String p_Datos ="";
        try {
            InputStream aux = p_sk.getInputStream();
            DataInputStream flujo = new DataInputStream(aux);
            p_Datos = flujo.readUTF();
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
        return p_Datos;
    }

    /*
     * Escribe dato en el socket cliente. Devuelve numero de bytes escritos,
     * o -1 si hay error.
     */
    public static void escribeSocket(Socket p_sk, String p_Datos) {
        try {
            OutputStream aux = p_sk.getOutputStream();
            DataOutputStream flujo = new DataOutputStream(aux);
            flujo.writeUTF(p_Datos);
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }
}
