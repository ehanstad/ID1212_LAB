import java.io.*;
import java.net.Socket;

public class WriteThread extends Thread {
  private Socket socket;
  private PrintStream out;
  private BufferedReader indata;

  public WriteThread(Socket socket) {
    this.socket = socket;
    try {
      this.out = new PrintStream(this.socket.getOutputStream());
      this.indata = new BufferedReader(new InputStreamReader(System.in));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void run() {
    String text;
    try {
      while ((text = indata.readLine()) != null) {
        out.println(text);
      }
      this.socket.shutdownOutput();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
