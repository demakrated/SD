import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.net.*;
import java.io.*;

public class Registro {         
    public static void main (String args[])     
    {            
        String URLRegistro;
        String id = args[0];
        String tipo = args[1];
        String invernadero = args[2];
        try           
            {   
            while(true){
                System.setSecurityManager(new RMISecurityManager());
                ObjetoRemoto objetoRemoto = new ObjetoRemoto(id, tipo, invernadero);
                
                //mis url seran del tipo 1 1 T --> invernadero id tipo
                URLRegistro = id+"-"+tipo+"-"+invernadero;
                Naming.rebind (URLRegistro, objetoRemoto);            
                System.out.println("Servidor de objeto preparado.");
                Thread.sleep(5000);
            }
        }            
        catch (Exception ex)            
        {                  
            System.out.println(ex);            
        }     
    }
}
