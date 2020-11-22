package examples;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class BeerClient {

    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 61298;

    public static void main(String[] args) {
	try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
		DataInputStream input = new DataInputStream(socket.getInputStream());
		DataOutputStream output = new DataOutputStream(socket.getOutputStream());
		Scanner sc = new Scanner(System.in);) {

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
