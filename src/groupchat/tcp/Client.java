package groupchat.tcp;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final int SERVER_PORT = 61298;
    private static final String SERVER_IP = "127.0.0.1";

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    private void start() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Nhap Name Client: ");
            String name = sc.nextLine();
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            ReadThreadClient readThread = new ReadThreadClient(socket);
            readThread.start();
            WriteThreadClient writeThread = new WriteThreadClient(socket, name);
            writeThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
