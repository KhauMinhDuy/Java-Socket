package groupchat.tcp;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ReadThreadClient extends Thread {

    private Socket socket;

    public ReadThreadClient(Socket socket) {
	this.socket = socket;
    }

    @Override
    public void run() {
	try {
	    while (true) {
		DataInputStream input = new DataInputStream(socket.getInputStream());
		String message = input.readUTF();
		System.out.println(message);
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
