import java.io.*;
import java.security.KeyStore;
import java.net.*;
import javax.net.ssl.*;
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

  void run() throws Exception {

    char[] password = "password".toCharArray();
    KeyStore ks = KeyStore.getInstance("jks");
    FileInputStream f = new FileInputStream("cert.cer");
    ks.load(f,password);

    KeyManagerFactory kmf = KeyManagerFactory.getInstance("RSA");
    kmf.init(ks,password);

    TrustManagerFactory tmf = TrustManagerFactory.getInstance("RSA");
    tmf.init(ks);
    SSLContext sslc = SSLContext.getInstance("TLS");
    sslc.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

    SSLServerSocketFactory factory = sslc.getServerSocketFactory();
    try (ServerSocket serverSocket = factory.createServerSocket(this.port)) {
      SSLServerSocket sslServerSocket = (SSLServerSocket) serverSocket;
      sslServerSocket.setNeedClientAuth(true);

      System.out.println("Server started on port " + this.port);

      while (true) {
        Socket clientSocket = sslServerSocket.accept();
        System.out.println("Client connected: " + clientSocket.getLocalAddress());

        HttpClientThread client = new HttpClientThread(clientSocket, this);
        client.start();
      }
    }
  }

  public static void main(String[] args) throws IOException {
    HttpServer server = new HttpServer(8080);
    try {
      server.run();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}
