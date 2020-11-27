import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.net.MalformedURLException;
import java.net.HttpURLConnection;
import java.io.*;

public class HttpURLConnectionClient {

	private String cookie;
	private String guess = "50";
	private int max = 100;
	private int min = 0;
	private int noGuesses = 0;

	public int play() {
		URL url = null;
		try {
			url = new URL("http://localhost:8888/index.html");
			HttpURLConnection con = null;
			con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("User-Agent", "Mozilla");
			con.connect();

			this.cookie = con.getHeaderField(3);
			game();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.noGuesses;
	}

	public void game() {
		URL url = null;
		try {
			url = new URL("http://localhost:8888/guess");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		while (true) {
			try {
				HttpURLConnection con = null;
				con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("POST");
				con.setRequestProperty("User-Agent", "Mozilla");
				con.setRequestProperty("Cookie", this.cookie);
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
				if (result.equals("higher.")) {
					this.min = Integer.parseInt(this.guess);
					this.noGuesses++;
					Integer guess = (this.min + ((this.max - this.min + 1) / 2));
					this.guess = guess.toString();
				} else if (result.equals("lower.")) {
					this.max = Integer.parseInt(this.guess);
					this.noGuesses++;
					Integer guess = (this.min + ((this.max - this.min) / 2));
					this.guess = guess.toString();
				} else {
					this.noGuesses++;
					System.out.println("Number of Guesses: " + this.noGuesses);
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		int totalGuesses = 0;
		int noGames = 100;

		for (int i = 0; i < noGames; i++) {
			HttpURLConnectionClient session = new HttpURLConnectionClient();
			int noGuesses = session.play();
			totalGuesses += noGuesses;
		}
		double avg = ((double) totalGuesses / (double) noGames);
		System.out.format("The average number of guesses is ≈ %.2f\n", avg);
	}
}
