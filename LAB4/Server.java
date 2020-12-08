import java.io.*;
import java.net.*;
import javax.net.ssl.*;

public class Server {

    public void init(String host, int port) {
      SSLSocketFactory sf = (SSLSocketFactory)SSLSocketFactory.getDefault();
      SSLSocket socket = null;

      try{
          socket = (SSLSocket)sf.createSocket(host,port);
          PrintWriter writer = null;
          BufferedReader reader = null;

          socket.startHandshake();
          writer = new PrintWriter(socket.getOutputStream());
          reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

          String str;

          while( (str=reader.readLine()) != null)
              System.out.println(str);
          writer.print("HELO");
          while( (str=reader.readLine()) != null)
              System.out.println(str);
          writer.close();
          reader.close();
          socket.close();
      }
      catch(Exception e){
          System.out.println(e.getMessage());
      }
    }

    public static void main(String[] args) {
      Server server = new Server();
      server.init("smtp.kth.se", 587);
    }
}
