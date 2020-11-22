package thread.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
	Client.start();
    }

    private static void start() {
	try (Socket socket = new Socket("127.0.0.1", 61298);
		DataOutputStream output = new DataOutputStream(socket.getOutputStream());
		DataInputStream input = new DataInputStream(socket.getInputStream());
		Scanner sc = new Scanner(System.in);) {
	    String exit = "";
	    while (!exit.equals("exit")) {
		exit = sc.nextLine();
		output.writeUTF(exit);
		System.out.println(input.readUTF());
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
