import java.util.Properties;

import javax.mail.*;
import com.sun.mail.imap.*;

public class Server {

  public String fetchMail(String host, int port, String username, String password) {

    Session session = this.getImapSession(host, port);

    try {
      Store store = session.getStore("imap");
      store.connect(host, port, username, password);

      IMAPFolder inbox = (IMAPFolder) store.getFolder("INBOX");
      inbox.open(Folder.READ_ONLY);

      Message[] messages = inbox.getMessages();
      System.out.println("messages.length = " + messages.length);
      System.out.println(messageToString(messages[messages.length - 1]));

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private Session getImapSession(String host, int port) {
    Properties props = new Properties();
    props.setProperty("mail.store.protocol", "imap");
    props.setProperty("mail.imap.host", host);
    props.put("mail.imap.port", port);
    props.put("mail.imap.ssl.enable", "true");
    Session session = Session.getDefaultInstance(props, null);

    session.setDebug(true);
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

}
