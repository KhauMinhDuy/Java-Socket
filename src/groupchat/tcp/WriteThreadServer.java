package groupchat.tcp;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class WriteThreadServer extends Thread {

    @Override
    public void run() {
	try (Scanner sc = new Scanner(System.in);) {
	    while (true) {
		String message = sc.nextLine();
		Server.getListConnect().stream().forEach(e -> {
		    try {
			DataOutputStream output = new DataOutputStream(e.getOutputStream());
			output.writeUTF("Server: " + message);
		    } catch (IOException e1) {
//			e1.printStackTrace();
		    }
		});
	    }
	}
    }
}
