package thitracnghiemudp;

import static thitracnghiemudp.Database.validate;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {

    private static final int SERVER_PORT = 61298;

    private static final byte[] BUFF = new byte[4096];

    public static void main(String[] args) {
	Server.start();
    }

    private static void start() {
	try (DatagramSocket server = new DatagramSocket(SERVER_PORT);) {
	    System.out.println(">>>> SERVER RUNNING <<<<");

	    DatagramPacket packet;

	    do {
		packet = new DatagramPacket(BUFF, BUFF.length);
		server.receive(packet);
		String username = new String(packet.getData(), 0, packet.getLength());

		packet = new DatagramPacket(BUFF, BUFF.length);
		server.receive(packet);
		String password = new String(packet.getData(), 0, packet.getLength());

		if (validate(username, password)) {

		    packet = new DatagramPacket("true".getBytes(), "true".length(), packet.getAddress(),
			    packet.getPort());
		    server.send(packet);
		    break;
		} else {
		    packet = new DatagramPacket("false".getBytes(), "false".length(), packet.getAddress(),
			    packet.getPort());
		    server.send(packet);
		}

	    } while (true);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
