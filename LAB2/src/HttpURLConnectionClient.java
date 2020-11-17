import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.net.MalformedURLException;
import java.net.HttpURLConnection;
import java.io.*;

public class HttpURLConnectionClient {

	private String cookieId;

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
		Scanner input = new Scanner(System.in);
		String line;
		URL url = null;
		try {
			url = new URL("http://localhost:8080/guess");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		while ((line = input.nextLine()) != null) {
			try {
				HttpURLConnection con = null;
				con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("POST");
				con.setRequestProperty("User-Agent", "Mozilla");
				con.setRequestProperty("Cookie", this.cookieId);
				String urlParameters = "guessedNumber=" + line;
				byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
				con.setRequestProperty("Content-Length", Integer.toString(postData.length));
				con.setDoOutput(true);
				try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
					wr.write(postData);
				}

				con.connect();
				System.out.println();

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
