package httpurlconnection;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Example {

    public static void main(String[] args) {
	try {
	    URL url = new URL("http://www.javatpoint.com/java-tutorial");
	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    for (int i = 1; i <= 8; i++) {
		System.out.println(connection.getHeaderFieldKey(i) + " = " + connection.getHeaderField(i));
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
