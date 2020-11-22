package groupchat.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static final int SERVER_PORT = 61298;
    private static List<Socket> listConnect = new ArrayList<>();

    public static List<Socket> getListConnect() {
        return listConnect;
    }

    public static void setListConnect(List<Socket> listConnect) {
        Server.listConnect = listConnect;
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    private void start() {
        try (ServerSocket server = new ServerSocket(SERVER_PORT);) {
            System.out.println(">>>> SERVER RUNNING <<<<");
            WriteThreadServer writeThreadServer = new WriteThreadServer();
            writeThreadServer.start();
            while (true) {
                Socket socket = server.accept();
                Server.getListConnect().add(socket);
                System.out.println(">>>> CLIENT CONNECT WITH " + socket + " <<<<");
                ReadThreadServer readThread = new ReadThreadServer(socket);
                readThread.start();
            }
        } catch (IOException e) {
//	    e.printStackTrace();
        }
    }

}
