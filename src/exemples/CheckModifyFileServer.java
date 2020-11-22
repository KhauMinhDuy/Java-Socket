package exemples;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class CheckModifyFileServer {

    public static void main(String[] args) {
	try {
	    URL url = new URL("https://www.tutorialspoint.com/cpp_standard_library/cpp_fstream_close.htm");
	    URLConnection connection = url.openConnection();
	    connection.setUseCaches(false);
	    long lastModified = connection.getLastModified();
	    System.out.println("Last Midify " + lastModified);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
