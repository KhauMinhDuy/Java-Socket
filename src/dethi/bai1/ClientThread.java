package dethi.bai1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ClientThread extends Thread {

    private Socket socket;
    private DataOutputStream output;
    private DataInputStream input;

    public ClientThread(Socket socket, DataInputStream input, DataOutputStream output) {
	this.socket = socket;
	this.input = input;
	this.output = output;
    }

    @Override
    public void run() {
	try {
	    String exit = "";
	    while (!exit.equalsIgnoreCase("Quit")) {
		exit = input.readUTF();
		switch (exit) {
		case "SET_SERVER_DIR": {
		    String setserverdir = input.readUTF();
		    Server.setServer_dir(setserverdir);
		    output.writeUTF("CHANGE SERVER_DIR SUCCESS");
		    break;
		}
		case "SEND": {
		    String sourceFile = input.readUTF();
		    String destFile = input.readUTF();
		    String clientDir = input.readUTF();
		    if (copyFileHandle(sourceFile, destFile, clientDir)) {
			output.writeUTF("SEND FILE SUCCESS");
		    } else {
			output.writeUTF("SEND FILE FAIL");
		    }
		    break;
		}
		case "GET": {
		    String sourceFile = input.readUTF();
		    String destFile = input.readUTF();
		    String clientDir = input.readUTF();
		    Path copy = Files.copy(Paths.get(Server.getServer_dir() + "/" + sourceFile),
			    Paths.get(clientDir + "/" + destFile));
		    if (copy != null) {
			output.writeUTF("GET FILE SUCCESS");

		    } else {
			output.writeUTF("GET FILE FAIL");
		    }
		    break;
		}
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    try {
		socket.close();
		input.close();
		output.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }

    private static boolean copyFileHandle(String sourceFile, String destFile, String clientDir) {
	try {
	    Path copy = Files.copy(Paths.get(sourceFile), Paths.get(Server.getServer_dir() + "\\" + destFile));
	    if (copy != null) {
		return true;
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return false;
    }
}
