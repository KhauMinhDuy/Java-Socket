package javatpoint.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {

	private static final int SERVER_PORT = 8888;
	private static final byte[] buff = new byte[1024];

	public static void start() {
		try (DatagramSocket server = new DatagramSocket(SERVER_PORT);) {
			DatagramPacket packet = new DatagramPacket(buff, buff.length);
			server.receive(packet);
			String s = new String(packet.getData(), 0, packet.getLength());
			System.out.println(s);
			int n = 10;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Server.start();
	}

}
