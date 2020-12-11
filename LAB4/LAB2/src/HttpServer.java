import java.io.*;
import java.net.*;
import java.security.KeyStore;
import java.util.Hashtable;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import java.util.Enumeration;

public class HttpServer {
  private int port;
  private Hashtable<String, Guess> sessions = new Hashtable<String, Guess>();
  private SSLContext context;
  private SSLServerSocketFactory socketFactory;
  private SSLServerSocket serverSocket;
  private KeyManagerFactory keyFactory;
  private KeyStore keyStore;
  private char[] password = "rihanna".toCharArray();

  public HttpServer(Integer port) throws Exception {
    InputStream inputStream = new FileInputStream(new File("../.keystore"));

    this.port = port;
    this.keyStore = KeyStore.getInstance("JKS", "SUN");
    this.keyStore.load(inputStream, this.password);
    this.context = SSLContext.getInstance("TLS");
    this.keyFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
    this.keyFactory.init(this.keyStore, this.password);
    this.context.init(this.keyFactory.getKeyManagers(), null, null);
    this.socketFactory = this.context.getServerSocketFactory();
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

  void run() throws IOException, Exception {
    this.serverSocket = (SSLServerSocket) this.socketFactory.createServerSocket(this.port);
    System.out.println("Server started on port " + this.port);

    while (true) {
      SSLSocket clientSocket = (SSLSocket) serverSocket.accept();
      System.out.println("Client connected: " + clientSocket.getLocalAddress());

      HttpClientThread client = new HttpClientThread(clientSocket, this);
      client.start();
    }
  }

  public static void main(String[] args) throws IOException {
    try {
      HttpServer server = new HttpServer(8080);
      server.run();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
