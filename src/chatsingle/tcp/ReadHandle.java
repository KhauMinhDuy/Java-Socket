package chatsingle.tcp;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ReadHandle extends Thread {

    private Socket socket;

    public ReadHandle() {
    }

    public ReadHandle(Socket socket) {
	this.socket = socket;
    }

    @Override
    public void run() {
	try {

	    DataInputStream input = new DataInputStream(socket.getInputStream());
	    String message = "";
	    while (!message.equalsIgnoreCase("exit")) {
		message = input.readUTF();
		System.out.println(message);
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }
}
