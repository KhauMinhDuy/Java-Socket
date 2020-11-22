package groupchat.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ReadThreadServer extends Thread {

    private Socket socket;

    public ReadThreadServer(Socket socket) {
	this.socket = socket;
    }

    @Override
    public void run() {
	try {
	    DataInputStream input = new DataInputStream(socket.getInputStream());
	    while (true) {
		String message = input.readUTF();
		Server.getListConnect().stream().filter(e -> e.getPort() != socket.getPort()).forEach(e -> {
		    try {
			DataOutputStream output = new DataOutputStream(e.getOutputStream());
			output.writeUTF(message);
		    } catch (IOException e1) {
//			e1.printStackTrace();
		    }
		});
		System.out.println(message);
	    }
	} catch (Exception e) {
//	    e.printStackTrace();
	}
    }
}
