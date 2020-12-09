import java.io.*;
import java.net.*;
import javax.net.ssl.*;
import java.util.Base64;

public class Send {

  private SSLSocket socket;
  private PrintWriter writer;
  private BufferedReader reader;

  private void printResponse(String command, String tag) throws IOException {
    this.writer.print(command);
    this.writer.flush();
    System.out.print(command);
    String response = getResponse(tag);
    System.out.println(response);
  }

  private String getResponse(String tag) throws IOException {
    StringBuilder sb = new StringBuilder();
    String str;
    while ((str = this.reader.readLine()) != null) {
      String[] response = str.split(" ");
      sb.append(str);
      sb.append("\n");
      for (int i = 0; i < response.length; i++) {
        if (response[i].equals(tag))
          return sb.toString();
      }
    }
    return sb.toString();
  }

  private void init(String host, int port) {

    SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
    SSLSocket socket = null;
    Socket s = null;
    String str;
    String helo = "EHLO client.example.com\r\n";
    String startTLS = "STARTTLS\r\n";

    try {
      s = new Socket(host, port);
      this.writer = new PrintWriter(s.getOutputStream());
      this.reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
      System.out.println("---- Connect without TLS ----");
      str = this.reader.readLine();
      System.out.println(str);
      printResponse(helo, "DSN");
      printResponse(startTLS, "TLS");
      socket = (SSLSocket) sf.createSocket(s, host, port, true);
      this.writer = new PrintWriter(socket.getOutputStream());
      this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      socket.setEnabledProtocols(socket.getSupportedProtocols());
      socket.setEnabledCipherSuites(socket.getSupportedCipherSuites());
      this.socket = socket;
      System.out.println("---- Connect with TLS ----");
      socket.startHandshake();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private String encode64(byte[] string) {
    return Base64.getEncoder().encodeToString(string);
  }

  private void sendMessage() {

    String helo = "EHLO client.example.com\r\n";
    String login = "AUTH LOGIN\r\n";
    String uname = encode64(Conf.username.getBytes()) + "\r\n";
    String password = encode64(Conf.password.getBytes()) + "\r\n";
    String mail_from = "MAIL FROM:<test@mail.com>\r\n";
    String rcpt_to = "RCPT TO:<ehanstad@kth.se>\r\n";
    String data = "DATA\r\n";
    String mail = "Visualizing the realism of life in actuality\n" + ".\n\r\n";
    String quit = "QUIT\r\n";

    try {

      printResponse(helo, "DSN");
      printResponse(login, "334");
      printResponse(uname, "334");
      this.writer.print(password);
      this.writer.flush();
      System.out.print(password);
      System.out.println(this.reader.readLine());
      printResponse(password, "235");
      printResponse(mail_from, "250");
      printResponse(rcpt_to, "250");
      printResponse(data, "354");
      printResponse(mail, "250");
      printResponse(quit, "221");

      this.writer.close();
      this.reader.close();
      this.socket.close();

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void main(String[] args) throws Exception {
    Send client = new Send();
    client.init("smtp.kth.se", 587);
    client.sendMessage();
  }
}
