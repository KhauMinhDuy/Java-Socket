package thread.demo2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Handler extends Thread {

    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;

    public Handler(Socket socket, DataInputStream input, DataOutputStream output) {
	this.socket = socket;
	this.input = input;
	this.output = output;
    }

    @Override
    public void run() {
	try {

	    int a = input.readInt();
	    int b = input.readInt();
	    output.writeInt((a + b));
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

}
