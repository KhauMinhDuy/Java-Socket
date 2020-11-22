package udp.bai1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {

	private static final int SERVER_PORT = 8888;
	private static final byte[] buf = new byte[4096];

	private static String chuyenCS(int n, int cs) {
		switch (cs) {
		case 2:
			return Integer.toBinaryString(n);
		case 8:
			return Integer.toOctalString(n);
		case 16:
			return Integer.toHexString(n);
		default:
			return "cs khong hop le";
		}
	}

	public static void start() {
		try (DatagramSocket server = new DatagramSocket(SERVER_PORT);) {
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			System.out.println(">>>> SERVER RUNNING <<<<");
			server.receive(packet);
			String s = new String(packet.getData(), 0, packet.getLength());
			int n = Integer.parseInt(s);
			packet = new DatagramPacket(buf, buf.length);
			server.receive(packet);
			s = new String(packet.getData(), 0, packet.getLength());
			int cs = Integer.parseInt(s);
			String chuyenCS = chuyenCS(n, cs);
			packet = new DatagramPacket(chuyenCS.getBytes(), chuyenCS.length(), packet.getAddress(), packet.getPort());
			server.send(packet);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		Server.start();
	}
}
