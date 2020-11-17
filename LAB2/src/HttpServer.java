import java.io.*;
import java.net.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class HttpServer {
  private int port;
  private Hashtable<String, Guess> sessions = new Hashtable<String, Guess>();

  public HttpServer(Integer port) {
    this.port = port;
  }

  public void saveSession(String sessionID) {
    sessions.put(sessionID, new Guess());
  }

  public Guess getSession(String sessionID) {
    Enumeration<String> enumer = sessions.keys();
    while (enumer.hasMoreElements()) {
      String nElem = enumer.nextElement();
      if (sessionID.contains(nElem)) {
        return sessions.get(nElem);
      }
    }
    return null;
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
