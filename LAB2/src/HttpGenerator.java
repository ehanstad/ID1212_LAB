public class HttpGenerator {
  private final static String http200 = "HTTP/1.1 200 OK\n" + "Server: Counting game 1.0\n"
      + "Content-Type: text/html\n";
  private final static String http404 = "HTTP/1.1 404 Not Found\n" + "Server: Counting game 1.0\n"
      + "Content-Type: text/html\n\n" + "404 Not Found";

  public static String getStartRes(String uid) {
    StringBuilder sb = new StringBuilder(http200);
    sb.append("Set-Cookie: uniqueID=" + uid + "\n");
    sb.append("\n");
    sb.append("<html>");
    sb.append(
        "<head><title>Game</title><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'></head>");
    sb.append("<body>");
    sb.append("<h2>Welcome to the number guess game!</h2>");
    sb.append(
        "<form action='/guess' method='post'><p>Im thinking of a number between 0 and 100.</p><input type='text' name='guessedNumber' /><input type='submit' value='submit' /></form>");
    sb.append("</body></html>");

    return sb.toString();
  }

  public static String getGuessRes(int guess, int noGuesses, short cmp) {
    StringBuilder sb = new StringBuilder(http200 + "\n");
    sb.append("<html><body>");
    if (cmp != 0) {
      if (cmp == -1) {
        sb.append("<h2>Nope, guess higher. You have made ");
      } else {
        sb.append("<h2>Nope, guess lower. You have made ");
      }
      sb.append(noGuesses + " guess(es)</h2>");
      sb.append("What's your guess?");
      sb.append("<form action='/guess' method='post' >");
      sb.append("<input type='text' name='guessedNumber' />");
      sb.append("<input type='submit' value='submit' /></form>");
    } else {
      sb.append("<h2>Correct</h2>");
    }
    sb.append("</html></body>");
    return sb.toString();
  }

  public static String getNotFoundRes() {
    return http404;
  }
}
