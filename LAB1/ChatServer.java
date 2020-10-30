import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

  private List<ClientThread> clients = new ArrayList<>();

  private void start() throws Exception {
    ServerSocket ss = new ServerSocket(1234);
    System.out.println("Server is open on port 1234");

    while (true) {
      Socket cs = ss.accept();
      ClientThread t = new ClientThread(cs, this);
      t.start();
      clients.add(t);
    }
  }

  public void forwardMessage(String message, ClientThread ct) {
    for(ClientThread client : clients) {
      if(client!=ct) {
        client.receiveMessage(message);
      }
    }
  }

  public static void main(String[] args) throws Exception {
    ChatServer cs = new ChatServer();
    cs.start();
  }
}
