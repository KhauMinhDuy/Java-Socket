package tcp.bai21;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private static final byte[] BUFF = new byte[4096];
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
			File file;
			do {
				file = new File(input.readUTF());
				if (file.exists()) {
					output.writeBoolean(true);
					break;
				} else {
					output.writeBoolean(false);
				}

			} while (true);
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);

			StringBuilder builder = new StringBuilder();
			String s = "";
			while ((s = reader.readLine()) != null) {
				builder.append(s);
			}

			String[] arrString = builder.toString().trim().split("\\s+");
			int max = arrString[0].length();
			builder.setLength(0);
			for (String value : arrString) {
				if (value.length() > max) {
					max = value.length();
					builder.setLength(0);
					builder.append(value + " ");
				} else if (value.length() == max) {
					builder.append(value + " ");
				}
			}

			output.writeUTF(builder.toString());
		} catch (IOException e) {

		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server.start();
	}

}
