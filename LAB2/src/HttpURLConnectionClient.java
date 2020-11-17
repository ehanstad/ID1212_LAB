import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.net.MalformedURLException;
import java.net.HttpURLConnection;
import java.io.*;

public class HttpURLConnectionClient {

	private String cookieId;
	private String guess = "50";
	private int overLimit = 100;
	private int underLimit = 0;
	private int noGuesses = 0;

	public void init() {
		URL url = null;
		try {
			url = new URL("http://localhost:8080/index.html");
			HttpURLConnection con = null;
			con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("User-Agent", "Mozilla");
			con.connect();

			this.cookieId = con.getHeaderField(3);
			game();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void game() {
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
					System.out.println(this.guess);
				} else if(result.equals("lower.")) {
					this.overLimit = Integer.parseInt(this.guess);
					this.noGuesses++;
					Integer guess = (this.underLimit + ((this.overLimit-this.underLimit)/2));
					this.guess = guess.toString();
					System.out.println(this.guess);
				} else {
					System.out.println("wohhhhhoooo");
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		HttpURLConnectionClient session = new HttpURLConnectionClient();
		session.init();
		// url = new URL("http://localhost:8080/index.html");

		// BufferedReader infile = null;
		// infile = new BufferedReader(new InputStreamReader(con.getInputStream()));
		// System.out.println(con.getRequestMethod());
		// String row = null;
		// while ((row = infile.readLine()) != null) {
		// System.out.println(row);
		// }
	}
}
