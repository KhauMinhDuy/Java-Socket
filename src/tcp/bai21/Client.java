package tcp.bai21;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	private static final String SERVER_IP = "127.0.0.1";
	private static final int SERVER_PORT = 8888;
	private static final byte[] BUFF = new byte[4096];

	public static void start() {
		try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
				DataInputStream input = new DataInputStream(
						socket.getInputStream());
				DataOutputStream output = new DataOutputStream(
						socket.getOutputStream());
				Scanner sc = new Scanner(System.in);) {
			do {
				System.out.print("Nhap duong dan file: ");
				output.writeUTF(sc.nextLine());
				if (input.readBoolean()) {
					System.out.println("Duong dan dung");
					break;
				} else {
					System.out.println("Duong dan khong dung. nhap lai");
				}

			} while (true);
			System.out.println("Nhung chu co do dai lon nhat la");
			System.out.println(input.readUTF());
		} catch (IOException e) {

		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client.start();
	}

}
