import java.io.*;
import java.net.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class HttpServer {
  private int port;
  private Hashtable<String, Guess> instances = new Hashtable<String, Guess>();

  public HttpServer(Integer port) {
    this.port = port;
  }

  public void saveInstance(String client) {
    System.out.println("saveInstance: " + client);
    instances.put(client, new Guess());
  }

  public Guess getInstance(String client) {
    System.out.println("GetInstance: " + client);
    Enumeration<String> enumer = instances.keys();
    Guess instance = null;
    while (enumer.hasMoreElements()) {
      String nElem = enumer.nextElement();
      if (client.contains(nElem)) {
        instance = instances.get(nElem);
      }
    }
    return instance;
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
