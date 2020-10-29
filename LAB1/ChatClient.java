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
      while( (text = indata.readLine()) != null){
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
      Socket socket = null;
      String text = "";
      while((socket=s) != null) {
        BufferedReader indata =
                new BufferedReader(new InputStreamReader(socket.getInputStream()));
        while( (text = indata.readLine()) != null){
          System.out.println("NEW MESSAGE: " + text);
        }
      }
      s.shutdownInput();
    } catch (IOException e) {
        System.out.println("ajaj");
    }
  }

  public void run() {
    if(indicator=='a'){
      writeMessage();
    }
    else {
      listenForMessage();
    }
  }

  public static void main(String[] args) throws Exception{
      Socket ws = new Socket("localhost",1234);
      Socket ls = new Socket("localhost",1234);
      Runnable wr = new ChatClient(ws,'a');
      (new Thread(wr)).start();
      Runnable lr = new ChatClient(ls,'b');
      (new Thread(lr)).start();
  }
}
