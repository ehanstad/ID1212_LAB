import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class HttpServer {
  private int port;

  public HttpServer(int port) {
    this.port = port;
  }

  void handleRequest(String req, Socket clientSocket) throws IOException {
    PrintStream res = new PrintStream(clientSocket.getOutputStream());
    StringTokenizer reqTokens = new StringTokenizer(req, " ?");
    reqTokens.nextToken(); // HTTP METHOD (GET, POST etc)
    String path = reqTokens.nextToken();

    File file = new File(".." + path);

    if (!"/favicon".equals(path)) {
      if (file.exists() && !file.isDirectory()) {
        res.println("HTTP/1.1 200 OK");
        res.println("Server: Counting game 1.0");
        if (path.indexOf(".html") != -1)
          res.println("Content-Type: text/html");
        if (path.indexOf(".gif") != -1)
          res.println("Content-Type: image/gif");
        res.println();

        FileInputStream in = new FileInputStream(file);
        byte[] b = new byte[1024];
        while (in.available() > 0) {
          res.write(b, 0, in.read(b));
        }
      } else {
        res.println("HTTP/1.1 404 Not Found");
        res.println("Server: Counting game 1.0");
        res.println("Content-Type: text/html");
      }
    }

    clientSocket.shutdownOutput();
    clientSocket.close();
  }

  void run() throws IOException {
    ServerSocket serverSocket = new ServerSocket(this.port);
    System.out.println("Server started on port " + this.port);
    while (true) {
      Socket clientSocket = serverSocket.accept();
      System.out.println("Client connected: " + clientSocket.getLocalAddress());

      BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      String req = in.readLine();
      System.out.println(req);
      handleRequest(req, clientSocket);
    }
  }

  public static void main(String[] args) throws IOException {
    HttpServer server = new HttpServer(8080);
    server.run();
  }
}