package thread;

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
	    while (true) {
		Socket socket = server.accept();
		DataInputStream input = new DataInputStream(socket.getInputStream());
		DataOutputStream output = new DataOutputStream(socket.getOutputStream());

		System.out.println("A new client is connected : " + socket);
		System.out.println("Assigning new thread for this client");
		Thread t = new ClientHandler(socket, input, output);
		t.start();

	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
