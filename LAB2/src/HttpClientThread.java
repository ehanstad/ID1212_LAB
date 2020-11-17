import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.util.UUID;

public class HttpClientThread extends Thread {

  private Socket socket;
  private HttpServer server;
  private PrintStream res;

  public HttpClientThread(Socket socket, HttpServer server) {
    this.socket = socket;
    this.server = server;
    try {
      this.res = new PrintStream(socket.getOutputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void createPostResault(StringTokenizer reqTokens) throws IOException {
    int guessedNumber = 0;
    String token;
    String uniqueID = null;
    while (reqTokens.hasMoreTokens()) {
      token = reqTokens.nextToken();
      if (token.contains("guessedNumber")) {
        guessedNumber = Integer.parseInt(reqTokens.nextToken());
      }
      if (token.equals("uniqueID")) {
        uniqueID = reqTokens.nextToken().toString();
      }
    }
    Guess guess = server.getInstance(uniqueID);
    if (!(guess == null)) {
      guess.setGuessedNumber(guessedNumber);
      int noGuesses = guess.getNoGuesses();
      short cmp = guess.checkGuess(guessedNumber);
      this.res.println(HttpGenerator.getGuessRes(guessedNumber, noGuesses, cmp));
    } else {
      System.out.println("guess is null");
    }
  }

  private void createGetResault(StringTokenizer reqTokens) throws IOException {
    String path = reqTokens.nextToken();
    if (!"/favicon".equals(path)) {
      if ("/index.html".equals(path)) {
        String uniqueID = UUID.randomUUID().toString();
        server.saveInstance(uniqueID);
        this.res.println(HttpGenerator.getStartRes(uniqueID));
      } else {
        this.res.println(HttpGenerator.getNotFoundRes());
      }
    }
  }

  void handleRequest(String req) throws IOException {
    StringTokenizer reqTokens = new StringTokenizer(req, " =?;\n");
    String method = reqTokens.nextToken();

    if ("POST".equals(method)) {
      createPostResault(reqTokens);
    } else if ("GET".equals(method)) {
      createGetResault(reqTokens);
    } else {
      this.res.println(HttpGenerator.getNotFoundRes());
    }
    this.socket.shutdownOutput();
    this.socket.close();
  }

  public void run() {
    while (!this.socket.isClosed()) {
      try {
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
