package httpurlconnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;

public class Demo {

    public static void main(String[] args) {
	try {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	    int n = Integer.parseInt(reader.readLine());
	    String key = "833921b016964f95905442e0fab0c229";
	    JSONObject ezm;

	    while (n-- > 0) {
		String image = reader.readLine();
		ezm = new JSONObject();
		ezm.put("url", image);

		URL url = new URL("https://westus.api.cognitive.microsoft.com/emotion/v1.0/recognize");

		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		con.setRequestMethod("POST");
		con.setRequestProperty("Ocp-Apim-Subscription-Key", key);
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		con.setFixedLengthStreamingMode(83);
		HttpURLConnection.setFollowRedirects(true);
		con.setInstanceFollowRedirects(false);

		con.setDoOutput(true);

		OutputStream out = con.getOutputStream();
		out.write(ezm.toString().getBytes());
		InputStream ip = con.getInputStream();
		BufferedReader br1 = new BufferedReader(new InputStreamReader(ip));

		System.out.println("Response Code:" + con.getResponseCode());
		System.out.println("Response Message:" + con.getResponseMessage());
		System.out.println("InstanceFollowRedirects:" + con.getInstanceFollowRedirects());

		// to print the 1st header field.
		System.out.println("Header field 1:" + con.getHeaderField(1));

		// to print if usingProxy flag set or not.
		System.out.println("Using proxy:" + con.usingProxy());

		StringBuilder response = new StringBuilder();
		String responseSingle = null;
		while ((responseSingle = br1.readLine()) != null) {
		    response.append(responseSingle);
		}
		String xx = response.toString();
		System.out.println(xx);

	    }
	} catch (NumberFormatException | IOException e) {
	    e.printStackTrace();
	}

    }
}
