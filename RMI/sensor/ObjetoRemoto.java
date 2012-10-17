import java.io.Serializable; 
import java.rmi.*;
import java.rmi.server.*;
import java.util.Calendar;

public class ObjetoRemoto extends UnicastRemoteObject
implements InterfazRemoto, Serializable 
{    
    private int id;
    private String tipo;
    private int valor;
    private String fecha;
    private String hora;
    private int invernadero;

	public ObjetoRemoto (String id, String tipo, String invernadero) throws RemoteException 
	{
        super();
        Calendar cal = Calendar.getInstance();
        int num = 0;
        String  sensor = "";
        fecha = "";
        num = cal.get(Calendar.DAY_OF_MONTH);   //dia
        fecha += num;
        num = cal.get(Calendar.MONTH);  //mes
        fecha += "/" + num;
        num = cal.get(Calendar.YEAR);   //año
        fecha += "/" + num + "@";
        hora += cal.get(Calendar.HOUR_OF_DAY);
        hora += ":" + cal.get(Calendar.MINUTE);
        valor = aleatorio(120, -20);
        this.id = Integer.parseInt(id);
        this.tipo = tipo;
        this.invernadero = Integer.parseInt(invernadero);

	}

	//genera numeros aleatorios entre el maximo y minimo dado
    @Override
    public int aleatorio(int max,int min){
        return (int)(Math.random()*(max-min))+min;      
    }
    
    @Override
    public int getId(){
        return id;
    }
    
    public int getInvernadero(){
        return invernadero;
    }
    
    @Override
    public String getTipo(){
        return tipo;
    }
    
    @Override
    public int getValor(){
        return valor;
    }
    
    @Override
    public String getFecha(){
        return fecha;
    }
    
    @Override
    public String getHora(){
        return hora;
    }
    
    public void tostring(){
        String resultado = "";
        System.out.println("#");
        System.out.println("ID: " + id);
        System.out.println("Tipo: " + tipo);
        System.out.println("Valor: " + valor);
        System.out.println("Fecha: " + fecha);
        System.out.println("Hora: " + hora);
        System.out.println("#");
        
    }
}
