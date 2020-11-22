package thread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientHandler extends Thread {

    private DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd");
    private DateFormat fortime = new SimpleDateFormat("hh:mm:ss");
    private final DataInputStream input;
    private final DataOutputStream output;
    private final Socket socket;

    public ClientHandler(Socket socket, DataInputStream input, DataOutputStream output) {
	this.socket = socket;
	this.input = input;
	this.output = output;
    }

    @Override
    public void run() {
	String received;
	String toreturn;
	while (true) {
	    try {
		output.writeUTF("What do you want?[Date | Time]..\n" + "Type Exit to terminate connection.");
		received = input.readUTF();

		if (received.equals("Exit")) {
		    System.out.println("Client " + this.socket + " sends exit...");
		    System.out.println("Closing this connection.");
		    System.out.println("Connection closed");
		    break;
		}

		Date date = new Date();
		switch (received) {

		case "Date":
		    toreturn = fordate.format(date);
		    output.writeUTF(toreturn);
		    break;

		case "Time":
		    toreturn = fortime.format(date);
		    output.writeUTF(toreturn);
		    break;

		default:
		    output.writeUTF("Invalid input");
		    break;
		}
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	try {
	    this.input.close();
	    this.output.close();
	    this.socket.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
