import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.*;

public class ChatServer implements Runnable {

  private Socket cs;

  public ChatServer(Socket cs) {
    this.cs = cs;
  }

  public void run() {
    try {
      String text = "";
      while (this.cs != null) {
        BufferedReader indata = new BufferedReader(new InputStreamReader(this.cs.getInputStream()));
        while ((text = indata.readLine()) != null) {
          System.out.println("Received: " + text);
          System.out.println("From: " + this.cs.getLocalAddress());
          forwardMessage(text);
        }
        this.cs.shutdownInput();
      }
    } catch (IOException e) {
      System.out.println("ajaj");
    }
  }

  private void forwardMessage(String text) {
    try {
      System.out.println("time to forward " + text);
      PrintWriter out = new PrintWriter(this.cs.getOutputStream(), true);
      out.println(text);
      out.flush();
    } catch (IOException e) {
      System.out.println("ajaj");
    }
  }

  public static void main(String[] args) throws Exception {
    ServerSocket ss = new ServerSocket(1234);
    System.out.println("Server is open on port 1234");
    while (true) {
      Socket cs = ss.accept();
      Runnable r = new ChatServer(cs);
      (new Thread(r)).start();
    }
  }
}
