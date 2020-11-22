package dethi.bai1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int SERVER_PORT = 12345;

    private static String server_dir = "dest";

    public static String getServer_dir() {
	return server_dir;
    }

    public static void setServer_dir(String server_dir) {
	Server.server_dir = server_dir;
    }

    public static void main(String[] args) {
	try (ServerSocket server = new ServerSocket(SERVER_PORT);) {
	    System.out.println(">>>> SERVSER RUNNING <<<<");

	    while (true) {
		Socket socket = server.accept();
		DataOutputStream output = new DataOutputStream(socket.getOutputStream());
		DataInputStream input = new DataInputStream(socket.getInputStream());
		System.out.println(">>>> CLIENT ACCPEPT WITH " + socket + " <<<<");
		Thread t = new ClientThread(socket, input, output);
		t.start();
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
