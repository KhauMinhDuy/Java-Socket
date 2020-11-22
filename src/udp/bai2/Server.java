package udp.bai2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {

	private static final byte[] BUFF = new byte[4096];
	private static final int SERVER_PORT = 8888;

	public static void start() {
		try (DatagramSocket server = new DatagramSocket(SERVER_PORT);) {
			DatagramPacket packet = new DatagramPacket(BUFF, BUFF.length);

		} catch (IOException e) {
			// TODO: handle exception
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server.start();
	}

}
