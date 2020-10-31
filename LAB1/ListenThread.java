import java.io.*;
import java.net.Socket;

public class ListenThread extends Thread {
  private Socket socket;
  private BufferedReader in;

  public ListenThread(Socket socket) {
    this.socket = socket;
    try {
      in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void run() {
    String text;
    try {
      while (this.socket != null) {
        while ((text = in.readLine()) != null) {
          System.out.println("[anonymous]: " + text);
        }
      }
      this.socket.shutdownInput();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
