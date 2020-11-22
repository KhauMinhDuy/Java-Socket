package examples;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BeerServer {

    private static final int SERVER_PORT = 61298;

    public static void main(String[] args) {

	try (ServerSocket server = new ServerSocket(61298);) {
	    System.out.println(">>>> SERVER RUNNING <<<<");
	    try (Socket socket = server.accept();
		    DataInputStream input = new DataInputStream(socket.getInputStream());
		    DataOutputStream output = new DataOutputStream(socket.getOutputStream());) {
		System.out.println(">>>> CLIENT ACCEPT <<<<");
		PrintStream printStream = new PrintStream(socket.getOutputStream());
		for (int i = 10; i >= 0; i--) {
		    printStream.println(i + " from java");
		}
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
