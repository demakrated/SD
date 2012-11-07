import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HiloControladorRMI extends Thread{
    
    //DATOS PRIVADOS
    private boolean terminar;
    InterfazRemoto objRemoto;

    //constantes
    private final int minT = -10;
    private final int maxT = 45;
    private final int minH = 0;
    private final int maxH = 100;
    private final String falloSensor = "Sensor estropeado. Cambiar sensor ";
    private final String Tbaja = "Activando calefactores";
    private final String Talta = "Activando ventilacion y abriendo ventanas";
    private final String Hbaja = "Activando goteo";
    private final String Halta = "Activando deshumidificacion";

    //PARTE PUBLICA
	
    //constructor a partir de la URL del registro
    HiloControladorRMI(String url) throws RemoteException{
  
        try
        {
            System.setSecurityManager(new RMISecurityManager());                
            objRemoto = (InterfazRemoto) Naming.lookup(url);
        }
        catch(Exception ex)
        {
            System.out.println("Error al instanciar el objeto remoto "+ex);
            System.exit(0);
        }
        terminar = false;
    }

    //ejecución de los hilos
    @Override
    public void run() {
        int resultado = 0;
        String Cadena = "";
        try{
                /*
                 * Se escribe en pantalla la informacion que se ha recibido del
                 * cliente
                 */
            resultado = this.realizarOperacion();
            Thread.sleep(3000);
            this.join();
        } catch (IOException ex) {
            Logger.getLogger(HiloControladorRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(InterruptedException e){
            System.out.println("ERROR:  " + e.toString());
        }
    }
    
    //se termina un hilo
    public void terminar(){
        terminar = true;
    }
    
    //parsea la cadena del socket y la interpreta para tomar medidas si es necesario
    public int realizarOperacion() throws InterruptedException, IOException {

        //parseo datos y lo smuestro tras recibir informacion de socket
      
        this.tostring();
        if(objRemoto.getTipo().equals("T")){    
            comprobarT();
        }
       else{
            comprobarH();
        }
        System.out.println();

        return 0;
    }
    
    //comprueba los datos de un sensor de temperatura y dice si hay fallo
    public void comprobarT() throws RemoteException{
        if(objRemoto.getValor() < minT){
            System.out.println("¡¡ATENCION!!");
            System.out.println(falloSensor + objRemoto.getId() + " en invernadero " + objRemoto.getInvernadero());
            System.out.println();
        }
        else if(objRemoto.getValor() < 25){
            System.out.println(Tbaja);
        }
        else if(objRemoto.getValor() > maxT){
            System.out.println("¡¡ATENCION!!");
            System.out.println(falloSensor + objRemoto.getId() + " en invernadero " + objRemoto.getInvernadero());
            System.out.println();
        }
        else{
            System.out.println(Talta);
        }
    }

    //comprueba los datos de un sensor de humedad y dice si hay fallo
    public void comprobarH() throws RemoteException{
        if(objRemoto.getValor() < minH){
            System.out.println("¡¡ATENCION!!");
            System.out.println(falloSensor + objRemoto.getId() + " en invernadero " + objRemoto.getInvernadero());
            System.out.println();
        }
        else if(objRemoto.getValor() < 35){ //comprobar humedad
            System.out.println(Hbaja);
        }
        else if(objRemoto.getValor() > maxH){
            System.out.println("¡¡ATENCION!!");
            System.out.println(falloSensor + objRemoto.getId() + " en invernadero " + objRemoto.getInvernadero());
            System.out.println();
        }
        else{
            System.out.println(Halta);
        }
    }

    //tostring
    public void tostring() throws RemoteException{

        System.out.println("#");
        System.out.println("Invernadero: " + objRemoto.getInvernadero());
        System.out.println("ID: " + objRemoto.getId());
        System.out.println("Tipo: " + objRemoto.getTipo());
        System.out.println("Valor: " + objRemoto.getValor());
        System.out.println("Fecha: " + objRemoto.getFecha());
        System.out.println("Hora: " + objRemoto.getHora());
        System.out.println("#");
    }




}