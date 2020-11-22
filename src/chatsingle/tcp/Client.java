package chatsingle.tcp;

import java.io.IOException;
import java.net.Socket;

public class Client {

    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 61298;

    public static void main(String[] args) {
	Client.start();
    }

    private static void start() {
	try {
	    Socket socket = new Socket(SERVER_IP, SERVER_PORT);
	    ReadHandle readHandle = new ReadHandle(socket);
	    readHandle.start();

	    WriteHandle writeHandle = new WriteHandle(socket, "Duy");
	    writeHandle.start();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
