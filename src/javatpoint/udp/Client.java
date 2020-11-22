package javatpoint.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {

	private static final int SERVER_PORT = 8888;

	public static void main(String[] args) {
		try (DatagramSocket client = new DatagramSocket(); Scanner sc = new Scanner(System.in);) {

			String s = sc.nextLine();
			InetAddress address = InetAddress.getByName("127.0.0.1");
			DatagramPacket packet = new DatagramPacket(s.getBytes(), s.length(), address, SERVER_PORT);
			client.send(packet);

		} catch (IOException e) {
		}
	}

}
