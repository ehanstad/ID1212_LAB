import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.net.MalformedURLException;
import java.net.HttpURLConnection;
import java.io.*;

public class HttpURLConnectionClient {

	private String cookieId;
	private String guess = "50";
	private int overLimit = 101;
	private int underLimit = 0;
	private int noGuesses = 1;

	public int init() {
		URL url = null;
		int noGuesses = 0;
		try {
			url = new URL("http://localhost:8080/index.html");
			HttpURLConnection con = null;
			con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("User-Agent", "Mozilla");
			con.connect();

			this.cookieId = con.getHeaderField(3);
			noGuesses = game();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return noGuesses;
	}

	public int game() {
		String line;
		URL url = null;
		try {
			url = new URL("http://localhost:8080/guess");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		while (true) {
			try {
				HttpURLConnection con = null;
				con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("POST");
				con.setRequestProperty("User-Agent", "Mozilla");
				con.setRequestProperty("Cookie", this.cookieId);
				String urlParameters = "guessedNumber=" + this.guess;
				byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
				con.setRequestProperty("Content-Length", Integer.toString(postData.length));
				con.setDoOutput(true);
				try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
					wr.write(postData);
				}

				con.connect();

				BufferedReader infile = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String[] res = infile.readLine().split(" ");
				String result = res[2];
				if(result.equals("higher.")) {
					this.underLimit = Integer.parseInt(this.guess);
					this.noGuesses++;
					Integer guess = (this.underLimit + ((this.overLimit-this.underLimit)/2));
					this.guess = guess.toString();
				} else if(result.equals("lower.")) {
					this.overLimit = Integer.parseInt(this.guess);
					this.noGuesses++;
					Integer guess = (this.underLimit + ((this.overLimit-this.underLimit)/2));
					this.guess = guess.toString();
				} else {
					System.out.println("Number of Guesses: " + this.noGuesses);
					return this.noGuesses;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		int guessList = 0;
		int noGames = 0;

		while (noGames<=100) {
			HttpURLConnectionClient session = new HttpURLConnectionClient();
			int noGuesses = session.init();
			guessList += noGuesses;
			noGames++;
		}
		System.out.println("The avrage number of guesses is: " + (guessList/noGames));
	}
}
