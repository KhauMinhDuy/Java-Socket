package bai1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 61298;

    public static void main(String[] args) {
	Client.start();
    }

    private static void start() {
	try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
		DataInputStream input = new DataInputStream(socket.getInputStream());
		DataOutputStream output = new DataOutputStream(socket.getOutputStream());
		Scanner sc = new Scanner(System.in);) {

	    do {
		System.out.print("Nhap username: ");
		String username = sc.nextLine();
		System.out.print("Nhap password: ");
		String password = sc.nextLine();

		output.writeUTF(username);
		output.writeUTF(password);
		int validate = input.readInt();

		System.out.println(input.readUTF());

		if (validate > 0) {
		    break;
		}
	    } while (true);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
