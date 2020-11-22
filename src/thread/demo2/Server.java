package thread.demo2;

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
	try {
	    ServerSocket server = new ServerSocket(SERVER_PORT);
	    System.out.println(">>>> server running <<<<");
	    while (true) {
		Socket socket = server.accept();
		DataInputStream input = new DataInputStream(socket.getInputStream());
		DataOutputStream output = new DataOutputStream(socket.getOutputStream());
		System.out.println("Client accept with " + socket);
		Thread thread = new Handler(socket, input, output);
		thread.start();
	    }
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}
