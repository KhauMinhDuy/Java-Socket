package thread.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatHandler extends Thread {

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    public ChatHandler() {
    }

    public ChatHandler(Socket socket, DataOutputStream output, DataInputStream input) {
	this.socket = socket;
	this.output = output;
	this.input = input;
    }

    @Override
    public void run() {
	String exit = "";
	try {
	    while (!exit.equalsIgnoreCase("exit")) {
		exit = input.readUTF();
		System.out.println(exit);
		output.writeUTF(exit);
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
