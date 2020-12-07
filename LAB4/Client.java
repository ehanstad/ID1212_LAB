import java.io.*;
import java.net.*;
import javax.net.ssl.*;

public class Client{

    public SSLSocket init(String host, int port){

      SSLSocketFactory sf = (SSLSocketFactory)SSLSocketFactory.getDefault();
      SSLSocket socket = null;

      try {
          socket = (SSLSocket)sf.createSocket(host,port);
          socket.startHandshake();
      } catch(Exception e){
          System.out.println(e.getMessage());
      }
      return socket;
    }

    public void fetchMessage(SSLSocket socket) {

      PrintWriter writer = null;
      BufferedReader reader = null;

      try {
        writer = new PrintWriter(
                      new BufferedWriter(
                      new OutputStreamWriter(
                      socket.getOutputStream())));
        reader = new BufferedReader(
                      new InputStreamReader(
                      socket.getInputStream()));

        String str;
        String login = "test test";

        while( (str=reader.readLine()) != null)
            System.out.println(str);
        writer.println("a001 login " + login);
        writer.println();
        writer.close();
        reader.close();
        socket.close();
      } catch(Exception e) {
          System.out.println(e.getMessage());
      }
    }

    public static void main(String[] args) throws Exception {
      Client client = new Client();
      SSLSocket socket = client.init("webmail.kth.se",993);
      client.fetchMessage(socket);
    }
}
