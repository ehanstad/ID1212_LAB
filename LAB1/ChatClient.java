import java.io.*;
import java.net.Socket;
import java.lang.*;

public class ChatClient implements Runnable {

  private Socket s;
  private char indicator;

  public ChatClient(Socket s, char indicator) {
    this.s = s;
    this.indicator = indicator;
  }

  private void writeMessage() {
    try {
      System.out.println("THERE");
      PrintStream out = new PrintStream(s.getOutputStream());
      BufferedReader indata = new BufferedReader(new InputStreamReader(System.in));
      String text;
      System.out.print("Enter text to send: ");
      while ((text = indata.readLine()) != null) {
        out.println(text);
        System.out.print("Enter text to send: ");
      }
      s.shutdownOutput();
    } catch (IOException e) {
      System.out.println("ajaj");
    }
  }

  private void listenForMessage() {
    try {
      System.out.println("HERE");
      String text = null;
      BufferedReader indata = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
      while (this.s != null) {
        while ((text = indata.readLine()) != null) {
          System.out.println("NEW MESSAGE: " + text);
        }
      }
      this.s.shutdownInput();
    } catch (IOException e) {
      System.out.println("ajajaj");
    }
  }

  public void run() {
    if (indicator == 'a') {
      writeMessage();
    } else {
      listenForMessage();
    }
  }

  public static void main(String[] args) throws Exception {
    Socket socket = new Socket("localhost", 1234);
    Runnable wr = new ChatClient(socket, 'a');
    (new Thread(wr)).start();
    Runnable lr = new ChatClient(socket, 'b');
    (new Thread(lr)).start();
  }
}
