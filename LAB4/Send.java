import java.io.*;
import java.net.*;
import javax.net.ssl.*;

public class Send {

  private SSLSocket socket;
  private PrintWriter writer;
  private BufferedReader reader;

    private void init(String host, int port) {

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
          e.printStackTrace();
      }
    }

    private void sendMessage() {

      String str;

      try {

        str = this.reader.readLine();
        System.out.println(str);

        this.writer.close();
        this.reader.close();
        this.socket.close();

      } catch(Exception e) {
          System.out.println(e.getMessage());
      }
    }

    public static void main(String[] args) throws Exception {
      Send client = new Send();
      client.init("smtp.kth.se",587);
      client.sendMessage();
    }
}
