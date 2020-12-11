import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MailService extends Remote {
  String fetchMail(String host, int port, String username, String password) throws RemoteException;
}
