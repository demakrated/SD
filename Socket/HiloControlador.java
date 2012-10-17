import java.io.IOException;
import java.net.Socket;

public class HiloControlador extends Thread {

    //datos privados
    private Socket skCliente;
    private boolean terminar;
    private final int minT = -10;
    private final int maxT = 45;
    private final int minH = 0;
    private final int maxH = 100;
    private final String falloSensor = "Sensor estropeado. Cambiar sensor ";
    private final String Tbaja = "Activando calefactores";
    private final String Talta = "Activando ventilacion y abriendo ventanas";
    private final String Hbaja = "Activando goteo";
    private final String Halta = "Activando deshumidificacion";


    //METODOS PUBLICOS

    //crea el hilo del servidor
    public HiloControlador(Socket p_cliente) {
        this.skCliente = p_cliente; terminar = false;
    }

    //comprueba los datos de un sensor de temperatura y dice si hay fallo
    public void comprobarT(String valor, String tipo){
        if(Integer.parseInt(valor) < minT){
            System.out.println("¡¡ATENCION!!");
            System.out.println(falloSensor + tipo);
            System.out.println();
        }
        else if(Integer.parseInt(valor) < 25){
            System.out.println(Tbaja);
        }
        else if(Integer.parseInt(valor) > maxT){
            System.out.println(falloSensor + tipo);
        }
        else{
            System.out.println(Talta);
        }
    }

    //comprueba los datos de un sensor de humedad y dice si hay fallo
    public void comprobarH(String valor, String tipo){
        if(Integer.parseInt(valor) < minH){
            System.out.println("¡¡ATENCION!!");
            System.out.println(falloSensor + tipo);
            System.out.println();
        }
        else if(Integer.parseInt(valor) < 40){
            System.out.println(Hbaja);
        }
        else if(Integer.parseInt(valor) > maxH){
            System.out.println(falloSensor + tipo);
        }
        else{
            System.out.println(Halta);
        }
    }

    //parsea la cadena del socket y la interpreta para tomar medidas si es necesario
    public int realizarOperacion(String p_Cadena) throws InterruptedException, IOException {
        System.out.println("Obtengo: " + p_Cadena);
        if(p_Cadena.equals("fin"))
        {
            System.out.println("fin");
            skCliente.close();
            this.join();
        }
        else
        {
            String[] datos = p_Cadena.split("@");
            if(datos.length != 6)
            {
                System.out.println("ERROR: Información incorrecta. Obtenido: " + p_Cadena);
            }
            else
            {
                //parseo datos y lo smuestro tras recibir informacion de socket
      
                System.out.println("#");
                System.out.println("Invernadero: " + datos[5]);
                System.out.println("ID: " + datos[0].substring(1));
                System.out.println("Tipo: " + datos[1]);
                System.out.println("Valor: " + datos[2]);
                System.out.println("Fecha: " + datos[3]);
                System.out.println("Hora: " + datos[4]);
                System.out.println("#");
                if(datos[1].equals("T")){
                    comprobarT(datos[2], datos[1]);
                }
                else{
                    comprobarH(datos[2], datos[1]);
                }
                System.out.println();
            }
        }
        return 0;
    }

    //setter para terminar con el hilo
    public void terminar(){
        terminar = true;
    }

    @Override
    public void run() {
        int resultado = 0;
        String Cadena = "";
        try{
            while (!terminar) {
                SocketHelper.escribeSocket(skCliente, "datos"); //le pido los datos al sensor
                Cadena = SocketHelper.leeSocket(skCliente); //los guardo
                /*
                 * Se escribe en pantalla la informacion que se ha recibido del
                 * cliente
                 */
                resultado = this.realizarOperacion(Cadena);
                Thread.sleep(3000);
            }
            SocketHelper.escribeSocket(skCliente, "fin");
            skCliente.close();
            this.join();
        }
        catch(InterruptedException e){
            System.out.println("ERROR:  " + e.toString());
        }
        catch(IOException e2){
            System.out.println("ERROR:  " + e2.toString());   
        }
    }
}
