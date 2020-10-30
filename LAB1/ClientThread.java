import java.io.*;
import java.net.*;
import java.lang.*;

public class ClientThread extends Thread {

  private Socket cs;
  private ChatServer server;

  public ClientThread(Socket cs, ChatServer server) {
    this.cs = cs;
    this.server = server;
  }

  public void run() {
    try {
      String text = "";
      while (this.cs != null) {
        BufferedReader indata = new BufferedReader(new InputStreamReader(this.cs.getInputStream()));
        while ((text = indata.readLine()) != null) {
          System.out.println("Received: " + text);
          System.out.println("From: " + this.cs.getLocalAddress());
          server.forwardMessage(text, this);
        }
        this.cs.shutdownInput();
      }
    } catch (IOException e) {
      System.out.println("ajaj");
    }
  }

  public void receiveMessage(String message) {
    try {
      PrintWriter out = new PrintWriter(this.cs.getOutputStream(), true);
      out.println(message);
      out.flush();
    } catch (IOException e) {
      System.out.println("ajaj");
    }
  }
}
