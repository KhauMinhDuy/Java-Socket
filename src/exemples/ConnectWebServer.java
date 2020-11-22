package exemples;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ConnectWebServer {

    public static void main(String[] args) {
	try {
	    Socket socket = new Socket("www.javatutorial.com", 80);
	    InetAddress address = socket.getInetAddress();
	    System.out.println("Connect to " + address);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
