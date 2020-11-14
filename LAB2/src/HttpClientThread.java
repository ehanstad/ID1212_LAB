import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class HttpClientThread extends Thread {

  private Socket socket;
  private ServerSocket server;
  private Guess instance;

  public HttpClientThread(Socket socket, ServerSocket server) {
    this.socket = socket;
    this.server = server;
    this.instance = new Guess();
  }

  private void createPostResault(PrintStream res, StringTokenizer reqTokens) throws IOException {
    while (reqTokens.hasMoreTokens()) {
      if ((reqTokens.nextToken()).contains("guessedNumber")) {
        this.instance.setGuessedNumber(Integer.parseInt(reqTokens.nextToken()));
      }
    }
    int noGuesses = this.instance.getNoGuesses();
    int guessedNumber = this.instance.getGuessedNumber();
    short cmp = this.instance.checkGuess(guessedNumber);
    res.println(HttpGenerator.getGuessRes(guessedNumber, noGuesses, cmp));
  }

  private void createGetResault(PrintStream res, StringTokenizer reqTokens) throws IOException {
    String path = reqTokens.nextToken();
    if (!"/favicon".equals(path)) {
      if ("/index.html".equals(path)) {
        System.out.println(HttpGenerator.getStartRes());
        res.println(HttpGenerator.getStartRes());
      } else {
        res.println(HttpGenerator.getNotFoundRes());
      }
    }
  }

  void handleRequest(String req) throws IOException {
    PrintStream res = new PrintStream(this.socket.getOutputStream());
    StringTokenizer reqTokens = new StringTokenizer(req, " =?");
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
