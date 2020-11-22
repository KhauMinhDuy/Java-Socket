package tcp.baikiemtra1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private static final int SERVER_PORT = 8888;

	public static void start() {

		try (ServerSocket server = new ServerSocket(SERVER_PORT);
				Socket socket = server.accept();
				DataInputStream input = new DataInputStream(
						socket.getInputStream());
				DataOutputStream output = new DataOutputStream(
						socket.getOutputStream());) {
			System.out.println(">>>> SERVER RUNNING <<<<");
			System.out.println(">>>> CLIENT KET NOI <<<<");
			int n;
			do {
				n = input.readInt();
				switch (n) {
					case 1 :
						output.writeUTF("1");
						break;
					case 2 :
						output.writeUTF("2");
						break;
					case 3 :
						output.writeUTF("3");
						break;
				}

			} while (n != 0);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		Server.start();
	}

}
