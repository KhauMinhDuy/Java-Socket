package thread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 61298;

    public static void main(String[] args) {
	Client.start();
    }

    private static void start() {
	try {
	    try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
		    DataInputStream dis = new DataInputStream(socket.getInputStream());
		    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
		    Scanner sc = new Scanner(System.in);) {

		while (true) {
		    System.out.println(dis.readUTF());
		    String tosend = sc.nextLine();
		    dos.writeUTF(tosend);

		    if (tosend.equals("Exit")) {
			System.out.println("Closing this connection : " + socket);
			System.out.println("Connection closed");
			break;
		    }

		    String received = dis.readUTF();
		    System.out.println(received);
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
