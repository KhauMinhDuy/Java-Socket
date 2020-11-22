package thitracnghiemudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {

    private static final int SERVER_PORT = 61298;
    private static final byte[] BUFF = new byte[4096];

    public static void main(String[] args) {
	Client.start();
    }

    private static void start() {
	try (DatagramSocket client = new DatagramSocket(); Scanner sc = new Scanner(System.in);) {

	    DatagramPacket packet;
	    InetAddress address = InetAddress.getByName("127.0.0.1");
	    do {
		System.out.print("Nhap username: ");
		String username = sc.nextLine();
		System.out.print("Nhap password: ");
		String password = sc.nextLine();

		packet = new DatagramPacket(username.getBytes(), username.length(), address, SERVER_PORT);
		client.send(packet);
		packet = new DatagramPacket(password.getBytes(), password.length(), address, SERVER_PORT);
		client.send(packet);

		packet = new DatagramPacket(BUFF, BUFF.length);
		client.receive(packet);
		String checkLogin = new String(packet.getData(), 0, packet.getLength());
		if (checkLogin.equals("true")) {
		    System.out.println("Dang nhap thanh cong");
		    break;
		} else {
		    System.out.println("Dang nhap that bai");
		}

	    } while (true);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
