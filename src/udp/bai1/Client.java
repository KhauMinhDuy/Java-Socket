package udp.bai1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

    private static final int SERVER_PORT = 8888;
    private static final byte[] buff = new byte[4096];

    public static void start() {
	try (DatagramSocket client = new DatagramSocket();
		InputStreamReader rd = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(rd);) {

	    InetAddress address = InetAddress.getByName("localhost");

	    String n;
	    do {
		System.out.println("Nhap n = ");
		n = reader.readLine();
		try {
		    Integer.parseInt(n);
		    if (Integer.parseInt(n) < 0) {
			System.out.println("Nhap sai. Nhap lai");
		    } else {
			break;
		    }
		} catch (Exception e) {
		    System.out.println("Nhap sai. Nhap lai");
		}
	    } while (true);

	    DatagramPacket packet = new DatagramPacket(n.getBytes(), n.length(), address, SERVER_PORT);
	    client.send(packet);

	    String cs;
	    do {
		System.out.println("Nhap vao co so");
		cs = reader.readLine();
		try {
		    Integer.parseInt(cs);
		    if (Integer.parseInt(cs) < 0
			    || (Integer.parseInt(cs) != 2 && Integer.parseInt(cs) != 8 && Integer.parseInt(cs) != 16)) {

			System.out.println("Nhap sai. Nhap lai");
		    } else {
			break;
		    }
		} catch (Exception e) {
		    System.out.println("Nhap sai. Nhap lai");
		}

	    } while (true);
	    packet = new DatagramPacket(cs.getBytes(), cs.length(), address, SERVER_PORT);
	    client.send(packet);

	    packet = new DatagramPacket(buff, buff.length);
	    client.receive(packet);
	    String rs = new String(packet.getData(), 0, packet.getLength());
	    System.out.println("Chuyen co so " + rs);

	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    public static void main(String[] args) {
	Client.start();
    }
}
