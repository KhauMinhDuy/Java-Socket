package bai1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int SERVER_PORT = 61298;

    public static void main(String[] args) {
	Server.start();
    }

    private static void start() {
	try (ServerSocket server = new ServerSocket(SERVER_PORT);) {
	    System.out.println(">>>> SERVER RUNNING <<<<");
	    while (true) {
		Socket socket = server.accept();
		DataOutputStream output = new DataOutputStream(socket.getOutputStream());
		DataInputStream input = new DataInputStream(socket.getInputStream());
		System.out.println(">>>> CLIENT CONNECT " + socket + " <<<<");
		Thread t = new ThreadHandler(socket, output, input);
		t.start();
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
