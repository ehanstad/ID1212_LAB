import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class HttpServer {
  private int port;
  private List<Guess> instances = new ArrayList<>();

  public HttpServer(int port) {
    this.port = port;
  }

  void run() throws IOException {
    ServerSocket serverSocket = new ServerSocket(this.port);
    System.out.println("Server started on port " + this.port);
    while (true) {
      Socket clientSocket = serverSocket.accept();
      System.out.println("Client connected: " + clientSocket.getLocalAddress());

      HttpClientThread client = new HttpClientThread(clientSocket, serverSocket);
      client.start();
    }
  }

  public static void main(String[] args) throws IOException {
    HttpServer server = new HttpServer(8080);
    server.run();
  }
}
