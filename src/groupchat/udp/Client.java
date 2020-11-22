package groupchat.udp;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client {

    private static final int SERVER_PORT = 61298;

    private DatagramPacket packet;
    private InetAddress address;

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    private void start() {
        try (DatagramSocket client = new DatagramSocket();
             Scanner sc = new Scanner(System.in);) {
            address = InetAddress.getByName("127.0.0.1");
            System.out.print("Enter a Name: ");
            String name = sc.nextLine();

            sendPacket(client, name);
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private void sendPacket(DatagramSocket client, String message) {
        packet = new DatagramPacket(message.getBytes(), message.length(), address, SERVER_PORT);
        try {
            client.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

