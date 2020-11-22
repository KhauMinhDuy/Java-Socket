package dethi.bai1tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {

    private static final int SERVER_PORT = 61298;

    public static void main(String[] args) {
	Server.start();
    }

    private static void start() {
	try (ServerSocket server = new ServerSocket(SERVER_PORT);) {
	    System.out.println(">>>> SERVER RUNNING <<<<");
	    try (Socket socket = server.accept();
		    DataInputStream input = new DataInputStream(socket.getInputStream());
		    DataOutputStream output = new DataOutputStream(socket.getOutputStream());) {
		System.out.println(">>>> CLIENT ACCEPT <<<<");

		int[] numbers = new int[4];
		for (int i = 0; i < 4; i++) {
		    numbers[i] = input.readInt();
		}

		Arrays.sort(numbers);

		for (int i : numbers) {
		    output.writeInt(i);
		}
	    }

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
