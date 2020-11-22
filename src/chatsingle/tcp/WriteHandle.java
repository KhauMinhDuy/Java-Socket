package chatsingle.tcp;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class WriteHandle extends Thread {

    private Socket socket;
    private String name;

    public WriteHandle() {
    }

    public WriteHandle(Socket socket, String name) {
	this.socket = socket;
	this.name = name;
    }

    @Override
    public void run() {
	try {
	    DataOutputStream output = new DataOutputStream(socket.getOutputStream());
	    Scanner sc = new Scanner(System.in);
	    String message = "";
	    while (!message.equalsIgnoreCase("exit")) {
		message = sc.nextLine();
		output.writeUTF(name + ": " + message);
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
