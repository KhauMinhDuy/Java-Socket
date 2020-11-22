package dethi.bai2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class Server {

    private static final byte[] BUFF = new byte[4096];

    public static void main(String[] args) {
	Server.start();
    }

    private static void start() {
	try (DatagramSocket server = new DatagramSocket(61298);) {
	    System.out.println(">>>> SERVER RUNNING <<<<");
	    DatagramPacket packet = null;

	    int[] numbers = new int[3];

	    for (int i = 0; i < 3; i++) {
		packet = new DatagramPacket(BUFF, BUFF.length);
		server.receive(packet);
		numbers[i] = Integer.parseInt(new String(packet.getData(), 0, packet.getLength()));
		if (numbers[i] % 2 == 0) {
		    packet = new DatagramPacket("true".getBytes(), "true".length(), packet.getAddress(),
			    packet.getPort());
		    server.send(packet);
		} else {
		    packet = new DatagramPacket("false".getBytes(), "false".length(), packet.getAddress(),
			    packet.getPort());
		    server.send(packet);
		    i--;
		}
	    }

	    int sum = Arrays.stream(numbers).sum();
	    packet = new DatagramPacket(String.valueOf(sum).getBytes(), String.valueOf(sum).length(),
		    packet.getAddress(), packet.getPort());
	    server.send(packet);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
