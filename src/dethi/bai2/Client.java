package dethi.bai2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {

    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 61298;
    private static final byte[] BUFF = new byte[4096];

    public static void main(String[] args) {
	Client.start();
    }

    private static void start() {
	try (DatagramSocket client = new DatagramSocket(); Scanner sc = new Scanner(System.in);) {
	    DatagramPacket packet = null;
	    InetAddress address = InetAddress.getByName(SERVER_IP);

	    int[] numbers = new int[3];
	    for (int i = 0; i < 3; i++) {
		System.out.print("Nhap so thu " + (i + 1) + ": ");
		try {
		    numbers[i] = sc.nextInt();
		    sc.nextLine();
		    packet = new DatagramPacket(String.valueOf(numbers[i]).getBytes(),
			    String.valueOf(numbers[i]).length(), address, SERVER_PORT);
		    client.send(packet);

		    packet = new DatagramPacket(BUFF, BUFF.length);
		    client.receive(packet);
		    if (new String(packet.getData(), 0, packet.getLength()).equals("true")) {
			System.out.println("So Chan. Nhap Tiep");
		    } else {
			System.out.println("So Le. Nhap Lai");
			i--;
		    }

		} catch (Exception e) {
		    i--;
		    System.out.println("So khong hop le. nhap lai");
		    sc.nextLine();
		}
	    }

	    packet = new DatagramPacket(BUFF, BUFF.length);
	    client.receive(packet);
	    System.out.println("Tong cua 3 so la: " + new String(packet.getData(), 0, packet.getLength()));
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
