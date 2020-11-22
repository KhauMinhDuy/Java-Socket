package udp.menu;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {

    private static final int SERVER_PORT = 61298;
    private static final byte[] BUFF = new byte[4096];

    public static void main(String[] args) {
	Client.start();
    }

    private static void start() {
	try (DatagramSocket client = new DatagramSocket(); Scanner sc = new Scanner(System.in);) {
	    DatagramPacket packet;
	    InetAddress address = InetAddress.getByName("127.0.0.1");
	    int choose = -1;
	    do {
		System.out.println("Nhap lua chon");
		System.out.println("1. Them");
		System.out.println("2. Sua");
		System.out.println("3. Xoa");
		System.out.println("0. Thoat");
		try {
		    choose = sc.nextInt();
		    sc.nextLine();
		    packet = new DatagramPacket(String.valueOf(choose).getBytes(), String.valueOf(choose).length(),
			    address, SERVER_PORT);
		    client.send(packet);
		} catch (Exception e) {
		    sc.nextLine();
		    System.out.println("Nhap sai. Nhap lai");
		    continue;
		}

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
		case 0: {
		    System.out.println("Ket Thuc");
		    break;
		}
		default: {
		    System.out.println("Nhap sai. Nhap lai");
		}
		}
	    } while (choose != 0);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
