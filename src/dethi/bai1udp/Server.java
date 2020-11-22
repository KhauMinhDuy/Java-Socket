package dethi.bai1udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class Server {

    private static final int SERVER_PORT = 61298;
    private static final byte[] BUFF = new byte[4096];

    public static void main(String[] args) {
	Server.start();
    }

    private static void start() {
	try (DatagramSocket server = new DatagramSocket(SERVER_PORT);) {
	    System.out.println(">>>> SERVER RUNNING <<<");

	    DatagramPacket packet = null;
	    int[] numbers = new int[4];
	    for (int i = 0; i < 4; i++) {
		packet = new DatagramPacket(BUFF, BUFF.length);
		server.receive(packet);
		numbers[i] = Integer.parseInt(new String(packet.getData(), 0, packet.getLength()));
	    }

	    Arrays.sort(numbers);

	    for (int i : numbers) {
		packet = new DatagramPacket(String.valueOf(i).getBytes(), String.valueOf(i).length(),
			packet.getAddress(), packet.getPort());
		server.send(packet);
	    }

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
