import java.net.Socket;

public class ChatClient {
  public static void main(String[] args) throws Exception {
    Socket socket = new Socket("localhost", 1234);
    WriteThread writeThread = new WriteThread(socket);
    writeThread.start();
    ListenThread listenThread = new ListenThread(socket);
    listenThread.start();
    System.out.println("Connected to chat room.");
  }
}
