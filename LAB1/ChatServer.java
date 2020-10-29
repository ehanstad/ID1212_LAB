import java.io.*;
import java.net.*;
import java.lang.*;

public class ChatServer implements Runnable {

  private Socket cs;

  public ChatServer(Socket cs) {
    this.cs = cs;
  }

  public void run() {
    try{
      Socket s = null;
      String text = "";
      while( (s = cs) != null){
          BufferedReader indata =
                  new BufferedReader(new InputStreamReader(s.getInputStream()));
          while( (text = indata.readLine()) != null){
              System.out.println("Received: " + text);
              System.out.println("From: " + s.getLocalAddress());
          }
      s.shutdownInput();
      }
    } catch (IOException e) {
        System.out.println("ajaj");
    }
  }

    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(1234);
        System.out.println("Server is open on port 1234");
        while(true) {
          Socket cs = ss.accept();
          Runnable r = new ChatServer(cs);
          (new Thread(r)).start();
        }
    }
}
