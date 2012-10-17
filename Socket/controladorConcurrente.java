import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class controladorConcurrente {

    /**
     * @param args
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException{
            if (args.length < 1) {
                System.out.println("Debe indicar el nombre del fichero");
                System.exit(1);
            }

            ArrayList<HiloControlador> hl = new ArrayList<HiloControlador>();

            while(true){
                FileReader fr = null;
                fr = new FileReader(args[0]);
                BufferedReader bf = new BufferedReader(fr);
                for(int i=0; i<hl.size(); i++){     //termino  los hilos anteriores
                    hl.get(i).terminar();
                }

                String aux = bf.readLine();
                while(aux != null){ //leo todos los sensores del fichero

                    String [] datos = aux.split(" ");
                    //primero invernadero, segundo ID, tercero tipo, cuarto ip, quinto puerto
                    HiloControlador hs = new HiloControlador(new Socket (datos[3], Integer.parseInt(datos[4])));
                    hl.add(hs);
                    hs.start();
                    aux = bf.readLine();
                }
                bf.close();
                fr.close();
                Thread.sleep(10000);
            }
    }
}
