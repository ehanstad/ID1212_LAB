import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class HttpClientThread extends Thread {

  private Socket socket;
  private ServerSocket server;
  private Guess instance;

  public HttpClientThread(Socket socket, ServerSocket server){
    this.socket = socket;
    this.server = server;
    this.instance = new Guess();
  }

  private void createPostResault(PrintStream res, StringTokenizer reqTokens) throws IOException {
    while(reqTokens.hasMoreTokens()){
      if((reqTokens.nextToken()).contains("guessedNumber")) {
        int guessedNumber = Integer.parseInt(reqTokens.nextToken());
        this.instance.setGuessedNumber(guessedNumber);
      }
    }
    short indicator = this.instance.checkGuess(this.instance.getGuessedNumber());
    res.println("HTTP/1.1 200 OK");
    res.println("Server: Counting game 1.0");
    res.println("Content-Type: text/html");
    res.println();

    res.println("<html><body>");
    if(indicator!=0) {
      if(indicator==-1){
        res.println("<h2>Nope, guess higher. You have made ");
      } else {
        res.println("<h2>Nope, guess lower. You have made ");
      }
      res.println(this.instance.getNoGuesses() + " guess(es)</h2>");
      res.println("What's your guess?");
      res.println("<form action='/send' method='post' >");
      res.println("<input type='text' name='guessedNumber' />");
      res.println("<input type='submit' value='submit' /></form>");
    } else {
      res.println("<h2>Correct</h2>");
    }
    res.println("</html></body>");
  }

  private void createGetResault(PrintStream res, StringTokenizer reqTokens) throws IOException {
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
        createBadResault(res);
      }
    }
  }

  private void createBadResault(PrintStream res) {
    res.println("HTTP/1.1 404 Not Found");
    res.println("Server: Counting game 1.0");
    res.println("Content-Type: text/html");
    res.println();
    res.println("404 Not Found");
  }

  void handleRequest(String req) throws IOException {
    PrintStream res = new PrintStream(this.socket.getOutputStream());
    StringTokenizer reqTokens = new StringTokenizer(req, " =?");
    String method = reqTokens.nextToken();

    if("POST".equals(method)) {
      createPostResault(res, reqTokens);
    } else if("GET".equals(method)) {
      createGetResault(res, reqTokens);
    } else {
      createBadResault(res);
    }
    this.socket.shutdownOutput();
    this.socket.close();
  }

  public void run() {
    while(!this.socket.isClosed()) {
      try {
        BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        StringBuilder req = new StringBuilder();

        do {
          req.append((char) this.socket.getInputStream().read());
        } while(this.socket.getInputStream().available()>0);
        handleRequest(req.toString());

      } catch(Exception e) {
        e.printStackTrace();
      }
    }
  }
}
