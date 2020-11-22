package tcp.baikiemtra1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	private static final String SERVER_IP = "127.0.0.1";
	private static final int SERVER_PORT = 8888;

	public static void start() {
		try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
				DataInputStream input = new DataInputStream(
						socket.getInputStream());
				DataOutputStream output = new DataOutputStream(
						socket.getOutputStream());
				Scanner sc = new Scanner(System.in);) {
			int choose = -1;
			do {
				System.out.println("Nhap Lua Chon");
				System.out.println(">> 1. ");
				System.out.println(">> 2. ");
				System.out.println(">> 3. ");
				System.out.println(">> 0. ");
				try {
					choose = sc.nextInt();
					output.writeInt(choose);
				} catch (Exception e) {
					System.out.println("Nhap sai. Nhap lai");
					sc.nextLine();
					choose = -1;
				}

				switch (choose) {
					case 1 :
						System.out.println(input.readUTF());
						break;
					case 2 :
						System.out.println(input.readUTF());
						break;
					case 3 :
						System.out.println(input.readUTF());
						break;
				}

			} while (choose != 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Client.start();
	}

}
