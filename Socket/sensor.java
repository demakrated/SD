import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class sensor {

    //genera numeros aleatorios entre el maximo y minimo dado
    public static int aleatorio(int max,int min){
        return (int)(Math.random()*(max-min))+min;      
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException{


        String[] datos;
        sensor cl = new sensor();
        int i = 0;
        String host;
        String parser ="";
        String puerto;
        if (args.length < 2) {
            System.out.println("Debe indicar nombre de fichero y linea (cada linea es un sensor distinto)");
            System.exit(-1);
        }

        //leo la linea deseada del fichero
        int linea = 0;
        FileReader fr = null;
        fr = new FileReader(args[0]);
        String leido = "";
        BufferedReader bf = new BufferedReader(fr);
        while(linea < Integer.parseInt(args[1])){
            linea++;
            leido = bf.readLine();
        }

        //lo corto en cada dato leido, invernadero, ID, tipo, ip, puerto
        datos = leido.split(" ");

        ServerSocket skServidor = new ServerSocket(Integer.parseInt(datos[4])); //contiene el puerto de escucha a la espera de peticiones
        
        Socket skCliente;
        boolean exit = false;
        while(true){
            
            skCliente = skServidor.accept(); // Crea objeto
            exit = false;
            while(!exit){
                parser = SocketHelper.leeSocket(skCliente);
                if(parser.equals("datos")){
                    Calendar cal = Calendar.getInstance();
                    int num = 0;
                    String  sensor = "";
                    String aux = "";
                    num = cal.get(Calendar.DAY_OF_MONTH);   //dia
                    aux += num;
                    num = cal.get(Calendar.MONTH);  //mes
                    aux += "/" + num;
                    num = cal.get(Calendar.YEAR);   //año
                    aux += "/" + num + "@";
                    num = cal.get(Calendar.HOUR_OF_DAY);
                    aux += num + ":" + cal.get(Calendar.MINUTE) + "@";
                    int random = aleatorio(120, -20);
                    sensor += "#" + datos[1] + "@" + datos[2] + "@" + random + "@" + aux + datos[0];   //sensor: # ID @ tipo @ random @ fecha-Hora @ invernadero 
                    SocketHelper.escribeSocket(skCliente, sensor);
                }
                else{
                    skCliente.close();
                    exit = true;
                }
            }
        }
    }

}
