import java.io.*;
import java.net.*;
import javax.net.ssl.*;

public class Client{

  private SSLSocket socket;
  private PrintWriter writer;
  private BufferedReader reader;

    private void init(String host, int port){

      SSLSocketFactory sf = (SSLSocketFactory)SSLSocketFactory.getDefault();
      SSLSocket socket = null;

      try {
          socket = (SSLSocket)sf.createSocket(host,port);
          socket.startHandshake();
          this.socket = socket;
          this.writer = new PrintWriter(this.socket.getOutputStream());
          this.reader = new BufferedReader(
                          new InputStreamReader(
                          socket.getInputStream()));
      } catch(Exception e) {
          System.out.println(e.getMessage());
      }
    }

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
      while((str=this.reader.readLine()) != null) {
        String[] response = str.split(" ");
        sb.append(str);
        sb.append("\n");
        if(response[0].equals(tag))
          break;
      }
      return sb.toString();
    }

    private void fetchMessage() {

      String str;
      String login = "a001 login uname password\r\n";
      String inbox_select = "a002 select inbox\r\n";
      String fetch_full = "a003 fetch 1 full\r\n";
      String fetch_body = "a004 fetch 1 body[header]\r\n";
      String logout = "a005 logout\r\n";

      try {

        str = this.reader.readLine();
        System.out.println(str);

        printResponse(login, "a001");
        printResponse(inbox_select, "a002");
        printResponse(fetch_full, "a003");
        printResponse(fetch_body, "a004");
        printResponse(logout, "a005");

        this.writer.close();
        this.reader.close();

        this.socket.close();

      } catch(Exception e) {
          System.out.println(e.getMessage());
      }
    }

    public static void main(String[] args) throws Exception {
      Client client = new Client();
      client.init("webmail.kth.se",993);
      client.fetchMessage();
    }
}
