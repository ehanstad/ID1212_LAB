import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
  public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
    MailService service = (MailService) Naming.lookup("rmi://localhost:4444/mail");
    System.out
        .println("****MESSAGE RECIEVED****\n" + service.fetchMail("webmail.kth.se", 993, Conf.username, Conf.password));
  }
}
