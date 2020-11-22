package bai1.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    private static final int SERVER_PORT = 61298;
    private static final byte[] BUFF = new byte[4096];

    private static DatagramPacket packet = null;
    private static InetAddress address = null;

    public static void main(String[] args) {
	Client.start();
    }

    private static void start() {
	try (DatagramSocket client = new DatagramSocket(); Scanner sc = new Scanner(System.in);) {
	    address = InetAddress.getLocalHost();
	    do {
		System.out.print("Nhap username: ");
		String username = sc.nextLine();
		System.out.print("Nhap password: ");
		String password = sc.nextLine();
		sendPacket(username, client);
		sendPacket(password, client);

		String validate = receivePacket(client);
		String msg = receivePacket(client);
		System.out.println(msg);

		if (Integer.parseInt(validate) > 0) {
		    break;
		}

	    } while (true);
	} catch (SocketException | UnknownHostException e) {
	    e.printStackTrace();
	}
    }

    private static void sendPacket(String messageSend, DatagramSocket client) {
	try {
	    packet = new DatagramPacket(messageSend.getBytes(), messageSend.length(), address, SERVER_PORT);
	    client.send(packet);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private static String receivePacket(DatagramSocket client) {
	try {
	    packet = new DatagramPacket(BUFF, BUFF.length);
	    client.receive(packet);
	    return new String(packet.getData(), 0, packet.getLength());
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return null;
    }
}
