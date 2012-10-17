import java.rmi.Remote;

public interface InterfazRemoto extends Remote {
    
    public int aleatorio(int a, int b)throws java.rmi.RemoteException;;
    public int getId()throws java.rmi.RemoteException;;
    public String getTipo()throws java.rmi.RemoteException;;
    public int getValor()throws java.rmi.RemoteException;;
    public String getFecha()throws java.rmi.RemoteException;;
    public String getHora()throws java.rmi.RemoteException;;
    public void tostring()throws java.rmi.RemoteException;;
    public int getInvernadero() throws java.rmi.RemoteException;;
    
}
