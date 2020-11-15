import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.util.UUID;

public class HttpClientThread extends Thread {

  private Socket socket;
  private HttpServer server;
  private Guess instance;

  public HttpClientThread(Socket socket, HttpServer server) {
    this.socket = socket;
    this.server = server;
  }

  private void createPostResault(PrintStream res, StringTokenizer reqTokens) throws IOException {
    int guessedNumber = 0;
    String token;
    String uniqueID = null;
    while (reqTokens.hasMoreTokens()) {
      token = reqTokens.nextToken();
      if (token.contains("guessedNumber")) {
        guessedNumber = Integer.parseInt(reqTokens.nextToken());
      }
      if (token.equals("uniqueID")) {
        uniqueID = reqTokens.nextToken();
      }
    }
    Guess guess = server.getInstance(uniqueID);
    this.instance = guess;

    this.instance.setGuessedNumber(guessedNumber);
    int noGuesses = this.instance.getNoGuesses();
    short cmp = this.instance.checkGuess(guessedNumber);
    res.println(HttpGenerator.getGuessRes(guessedNumber, noGuesses, cmp));
  }

  private void createGetResault(PrintStream res, StringTokenizer reqTokens) throws IOException {
    String path = reqTokens.nextToken();
    if (!"/favicon".equals(path)) {
      this.instance = new Guess();
      String uniqueID = UUID.randomUUID().toString();
      server.saveInstance(this.instance, uniqueID);
      if ("/index.html".equals(path)) {
        res.println(HttpGenerator.getStartRes(uniqueID));
      } else {
        res.println(HttpGenerator.getNotFoundRes());
      }
    }
  }

  void handleRequest(String req) throws IOException {
    PrintStream res = new PrintStream(this.socket.getOutputStream());
    StringTokenizer reqTokens = new StringTokenizer(req, " =?;\n");
    String method = reqTokens.nextToken();

    if ("POST".equals(method)) {
      createPostResault(res, reqTokens);
    } else if ("GET".equals(method)) {
      createGetResault(res, reqTokens);
    } else {
      res.println(HttpGenerator.getNotFoundRes());
    }
    this.socket.shutdownOutput();
    this.socket.close();
  }

  public void run() {
    while (!this.socket.isClosed()) {
      try {
        BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        StringBuilder req = new StringBuilder();

        do {
          req.append((char) this.socket.getInputStream().read());
        } while (this.socket.getInputStream().available() > 0);
        handleRequest(req.toString());

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
