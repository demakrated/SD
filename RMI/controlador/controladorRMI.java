import java.io.*;
import java.rmi.*;
import java.util.ArrayList;

public class controladorRMI {
    

    /** Creates a new instance of cliente_rmi */
    public controladorRMI() {
    } 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException{
        if (args.length < 1) {
            System.out.println("Debe indicar el nombre del fichero");
            System.exit(1);
        }


        ArrayList<HiloControladorRMI> hl = new ArrayList<HiloControladorRMI>();

        while(true){
            FileReader fr = null;
            fr = new FileReader(args[0]);
            BufferedReader bf = new BufferedReader(fr);
            for(int i=0; i<hl.size(); i++){     //termino  los hilos anteriores
                hl.get(i).terminar();
            }

            String datos = bf.readLine();
            while(datos != null){ //leo todos los sensores del fichero

                //leo la url para generar el objeto
                HiloControladorRMI hs;
                hs = new HiloControladorRMI(datos);
                hl.add(hs);
                hs.start();
                datos = bf.readLine();
            }
            bf.close();
            fr.close();
            Thread.sleep(10000);
        }    
    }
}
