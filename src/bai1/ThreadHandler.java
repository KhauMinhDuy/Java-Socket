package bai1;

import static bai1.DAO.validate;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ThreadHandler extends Thread {

    private Socket socket;
    private DataOutputStream output;
    private DataInputStream input;

    public ThreadHandler() {
    }

    public ThreadHandler(Socket socket, DataOutputStream output, DataInputStream input) {
	this.socket = socket;
	this.output = output;
	this.input = input;
    }

    @Override
    public void run() {
	try {
	    do {
		String username = input.readUTF();
		String password = input.readUTF();
		int validate = validate(username, password);
		output.writeInt(validate);
		if (validate > 0) {
		    output.writeUTF("So Tien la: " + validate);
		    break;
		} else if (validate == -1) {
		    output.writeUTF("Sai password");
		} else if (validate == -2) {
		    output.writeUTF("Username khong ton tai");
		}

	    } while (true);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
