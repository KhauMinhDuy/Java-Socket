package chatsingle.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int SERVER_PORT = 61298;

    public static void main(String[] args) {
	Server.start();
    }

    private static void start() {
	try (ServerSocket server = new ServerSocket(SERVER_PORT);) {
	    System.out.println(">>>> SERVER RUNNING <<<<");
	    Socket socket = server.accept();
	    System.out.println(">>>> CLIENT CONNECT <<<<");

	    ReadHandle readHandle = new ReadHandle(socket);
	    readHandle.start();

	    WriteHandle writeHandle = new WriteHandle(socket, "Server");
	    writeHandle.start();

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
