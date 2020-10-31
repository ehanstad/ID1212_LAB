import java.io.*;
import java.net.*;

public class ClientThread extends Thread {

  private Socket socket;
  private ChatServer server;

  public ClientThread(Socket socket, ChatServer server) {
    this.socket = socket;
    this.server = server;
  }

  public void run() {
    try {
      String text = "";
      while (this.socket != null) {
        BufferedReader indata = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        while ((text = indata.readLine()) != null) {
          System.out.println("Received: " + text);
          System.out.println("From: " + this.socket.getLocalAddress());
          server.forwardMessage(text, this);
        }
        this.socket.shutdownInput();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void receiveMessage(String message) {
    try {
      PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
      out.println(message);
      out.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
