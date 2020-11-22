package groupchat.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server {

    private static final int SERVER_PORT = 61298;
    private static final byte[] BUFF = new byte[4096];
    private DatagramPacket packet;

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    private void start() {
        try (DatagramSocket server = new DatagramSocket(SERVER_PORT)) {
            System.out.println(">>>> SERVER RUNNING <<<<");

            String name = receivePacket(server);
            System.out.println(name);

        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private String receivePacket(DatagramSocket server) {
        packet = new DatagramPacket(BUFF, BUFF.length);
        try {
            server.receive(packet);
            return new String(packet.getData(), 0, packet.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
