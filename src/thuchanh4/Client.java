package thuchanh4;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Client {

    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 61298;
    private static final byte[] BUFF = new byte[4096];

    private DatagramPacket packet;
    private InetAddress address;

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    private void start() {
        try (DatagramSocket client = new DatagramSocket();) {
            address = InetAddress.getByName(SERVER_IP);
            List<String> lines = Files.readAllLines(Paths.get("bangdiem.txt"));
            sendPacket(client, String.valueOf(lines.size()));
            lines.forEach(e -> {
                String[] data = e.split(",");
                String hoTen = data[0];
                String maSV = data[1];
                String idLop = data[2];
                float dbt1 = Float.parseFloat(data[3]);
                float dbt2 = Float.parseFloat(data[4]);
                float dbt3 = Float.parseFloat(data[5]);
                sendPacket(client, hoTen);
                sendPacket(client, maSV);
                sendPacket(client, idLop);
                sendPacket(client, String.valueOf(dbt1));
                sendPacket(client, String.valueOf(dbt2));
                sendPacket(client, String.valueOf(dbt3));
            });

            String dgk = receivePacket(client);
            System.out.printf("%-20s | %-10s\n", "Ho Ten", "Diem GK");
            System.out.println(dgk);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendPacket(DatagramSocket client, String message) {
        try {
            packet = new DatagramPacket(message.getBytes(), message.length(), address, SERVER_PORT);
            client.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String receivePacket(DatagramSocket client) {
        try {
            packet = new DatagramPacket(BUFF, BUFF.length);
            client.receive(packet);
            return new String(packet.getData(), 0 , packet.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
