package dethi.bai1udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Scanner;

public class Client {

    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 61298;
    private static final byte[] BUFF = new byte[40096];

    public static void main(String[] args) {
	Client.start();
    }

    private static void start() {
	try (DatagramSocket client = new DatagramSocket(); Scanner sc = new Scanner(System.in);) {

	    DatagramPacket packet;
	    InetAddress address = InetAddress.getByName(SERVER_IP);

	    int[] numbers = new int[4];
	    System.out.println("Nhap vao 4 so");
	    for (int i = 0; i < 4; i++) {
		try {
		    numbers[i] = sc.nextInt();
		    sc.nextLine();
		    packet = new DatagramPacket(String.valueOf(numbers[i]).getBytes(),
			    String.valueOf(numbers[i]).length(), address, SERVER_PORT);
		    client.send(packet);
		} catch (Exception e) {
		    i--;
		    System.out.println("So khong hop le. nhap lai");
		    sc.nextLine();
		}
	    }

	    for (int i = 0; i < 4; i++) {
		packet = new DatagramPacket(BUFF, BUFF.length);
		client.receive(packet);
		numbers[i] = Integer.parseInt(new String(packet.getData(), 0, packet.getLength()));
	    }

	    System.out.println("Danh sach da sap xep");
	    Arrays.stream(numbers).forEach(System.out::println);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
