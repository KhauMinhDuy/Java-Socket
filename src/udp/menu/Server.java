package udp.menu;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {

    private static final byte[] BUFF = new byte[4096];

    public static void main(String[] args) {
	Server.start();
    }

    private static void start() {
	try (DatagramSocket server = new DatagramSocket(61298);) {
	    DatagramPacket packet;
	    int choose;
	    do {
		packet = new DatagramPacket(BUFF, BUFF.length);
		server.receive(packet);
		String n = new String(packet.getData(), 0, packet.getLength());
		choose = Integer.parseInt(n);
		switch (choose) {
		case 1: {
		    System.out.println("1");
		    break;
		}
		case 2: {
		    System.out.println("2");
		    break;
		}
		case 3: {
		    System.out.println("3");
		    break;
		}
		}
	    } while (choose != 0);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
