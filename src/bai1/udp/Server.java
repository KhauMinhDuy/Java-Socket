package bai1.udp;

import static bai1.DAO.validate;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server {

    private static final int SERVER_PORT = 61298;
    private static final byte[] BUFF = new byte[4096];
    private static DatagramPacket packet = null;

    public static void main(String[] args) {
	Server.start();
    }

    private static void start() {
	try (DatagramSocket server = new DatagramSocket(SERVER_PORT);) {
	    System.out.println(">>>> SERVER RUNNING <<<<");
	    do {
		String username = receivePacket(server);
		String password = receivePacket(server);
		System.out.println(username + " " + password);
		System.out.println(packet);
		int validate = validate(username, password);
		sendPacket(String.valueOf(validate), server);
		if (validate > 0) {
		    sendPacket("So Tien la: " + validate, server);
		    break;
		} else if (validate == -1) {
		    sendPacket("Sai password", server);
		} else if (validate == -2) {
		    sendPacket("Username khong ton tai", server);
		}
	    } while (true);

	} catch (SocketException e) {
	    e.printStackTrace();
	}
    }

    private static String receivePacket(DatagramSocket server) {
	try {
	    packet = new DatagramPacket(BUFF, BUFF.length);
	    server.receive(packet);
	    return new String(packet.getData(), 0, packet.getLength());
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return null;
    }

    private static void sendPacket(String msg, DatagramSocket server) {
	try {
	    packet = new DatagramPacket(msg.getBytes(), msg.length(), packet.getAddress(), packet.getPort());
	    server.send(packet);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
