package dethi.bai1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 12345;
    private static String CLIENT_DIR = "source";

    public static void main(String[] args) {
	try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
		DataOutputStream output = new DataOutputStream(socket.getOutputStream());
		DataInputStream input = new DataInputStream(socket.getInputStream());
		Scanner sc = new Scanner(System.in);) {
	    String exit = "";
	    while (!exit.equalsIgnoreCase("quit")) {
		System.out.println("Nhap lenh: ");
		String[] statements = sc.nextLine().split(" ");
		exit = statements[0];
		output.writeUTF(exit);
		switch (exit) {
		case "SET_SERVER_DIR": {
		    String setServerDir = statements[1];
		    output.writeUTF(setServerDir);
		    String message = input.readUTF();
		    System.out.println(message);
		    break;
		}
		case "SET_CLIENT_DIR": {
		    String setClientDir = statements[1];
		    CLIENT_DIR = setClientDir;
		    System.out.println("CHANGE CLIENT_DIR SUCCESS");
		    break;
		}
		case "SEND": {
		    String sourceFile = CLIENT_DIR + "/" + statements[1];
		    String destFile = statements[2];
		    output.writeUTF(sourceFile);
		    output.writeUTF(destFile);
		    output.writeUTF(CLIENT_DIR);
		    String message = input.readUTF();
		    System.out.println(message);
		    break;
		}
		case "GET": {
		    String sourceFile = statements[1];
		    String destFile = statements[2];
		    output.writeUTF(sourceFile);
		    output.writeUTF(destFile);
		    output.writeUTF(CLIENT_DIR);
		    String message = input.readUTF();
		    System.out.println(message);
		    break;
		}
		}
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
