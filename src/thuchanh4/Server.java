package thuchanh4;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import static thuchanh4.DAO.*;

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
            List<Student> studentList = new ArrayList<>();
            String size = receivePacket(server);
            for(int i = 0; i < Integer.parseInt(size); i++) {
                String hoTen = receivePacket(server);
                String maSv = receivePacket(server);
                String idLop = receivePacket(server);
                String dbt1 = receivePacket(server);
                String dbt2 = receivePacket(server);
                String dbt3 = receivePacket(server);
                studentList.add(new Student(hoTen, maSv, idLop, Float.parseFloat(dbt1), Float.parseFloat(dbt2), Float.parseFloat(dbt3)));
            }
            insertAllStudent(studentList);
            String dgk = getAllDGK();
            sendPacket(server, dgk);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private void sendPacket(DatagramSocket sever, String message) {
        try {
            packet = new DatagramPacket(message.getBytes(), message.length(), packet.getAddress(), packet.getPort());
            sever.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String receivePacket(DatagramSocket sever) {
        try {
            packet = new DatagramPacket(BUFF, BUFF.length);
            sever.receive(packet);
            return new String(packet.getData(), 0 , packet.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
