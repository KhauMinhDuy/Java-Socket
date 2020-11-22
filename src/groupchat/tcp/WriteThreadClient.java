package groupchat.tcp;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class WriteThreadClient extends Thread {

    private Socket socket;
    private String name;

    public WriteThreadClient(Socket socket, String name) {
	this.socket = socket;
	this.name = name;
    }

    @Override
    public void run() {

	try {
	    Scanner sc = new Scanner(System.in);
	    while (true) {
		DataOutputStream output = new DataOutputStream(socket.getOutputStream());
		String message = sc.nextLine();
		output.writeUTF(name + ": " + message);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
