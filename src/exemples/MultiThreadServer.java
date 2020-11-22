package exemples;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadServer {

    public static void main(String[] args) {
	try {
	    ServerSocket server = new ServerSocket(61298);

	    while (true) {
		Socket socket = server.accept();
		System.out.println("Connect");

	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
