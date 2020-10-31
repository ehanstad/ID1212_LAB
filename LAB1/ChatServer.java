import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

  private List<ClientThread> clients = new ArrayList<>();

  private void start() throws Exception {
    ServerSocket serverSocket = new ServerSocket(1234);
    System.out.println("Server started on port 1234");

    while (true) {
      Socket clientSocket = serverSocket.accept();
      ClientThread clientThread = new ClientThread(clientSocket, this);
      clientThread.start();
      clients.add(clientThread);
    }
  }

  public void forwardMessage(String message, ClientThread ct) {
    for (ClientThread client : clients) {
      if (client != ct) {
        client.receiveMessage(message);
      }
    }
  }

  public static void main(String[] args) throws Exception {
    ChatServer chatServer = new ChatServer();
    chatServer.start();
  }
}
