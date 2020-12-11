import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;

import javax.mail.*;
import com.sun.mail.imap.*;

public class Server extends UnicastRemoteObject implements MailService {

  public Server() throws RemoteException {
    super();
  }

  public String fetchMail(String host, int port, String username, String password) {

    Session session = this.getImapSession(host, port);

    try {
      Store store = session.getStore("imap");
      store.connect(host, port, username, password);

      IMAPFolder inbox = (IMAPFolder) store.getFolder("INBOX");
      inbox.open(Folder.READ_ONLY);

      Message[] messages = inbox.getMessages();
      return messageToString(messages[messages.length - 1]);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private Session getImapSession(String host, int port) {
    Properties props = new Properties();
    props.setProperty("mail.store.protocol", "imap");
    props.put("mail.imap.host", host);
    props.put("mail.imap.port", port);
    props.put("mail.imap.ssl.enable", "true");

    Session session = Session.getDefaultInstance(props, null);
    return session;
  }

  private String messageToString(Message message) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("---------------------------------\n");
    sb.append("Subject: " + message.getSubject() + "\n");
    sb.append("From: " + message.getFrom() + "\n");
    sb.append("Text: " + message.getContent().toString() + "\n");
    return sb.toString();
  }

  public static void main(String[] args) throws RemoteException {
    Registry registry = LocateRegistry.createRegistry(4444);
    registry.rebind("mail", new Server());
  }

}
