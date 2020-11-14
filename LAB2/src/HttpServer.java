import java.io.*;
import java.net.*;
import java.util.Hashtable;

public class HttpServer {
  private int port;
  private Hashtable<String, Guess> instances = new Hashtable<String, Guess>();

  public HttpServer(Integer port) {
    this.port = port;
  }

  public void saveInstance(Guess guess, String client) {
    System.out.println("saveInstance: " + client);
    instances.put(client, guess);
  }

  public Guess getInstance(String client) {
    System.out.println("GetInstance: " + client);
    return instances.get(client);
  }

  void run() throws IOException {
    ServerSocket serverSocket = new ServerSocket(this.port);
    System.out.println("Server started on port " + this.port);
    while (true) {
      Socket clientSocket = serverSocket.accept();
      System.out.println("Client connected: " + clientSocket.getLocalAddress());

      HttpClientThread client = new HttpClientThread(clientSocket, this);
      client.start();
    }
  }

  public static void main(String[] args) throws IOException {
    HttpServer server = new HttpServer(8080);
    server.run();
  }
}
