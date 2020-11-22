package thuchanh2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    private static final int SERVER_PORT = 61298;
    private static final String SERVER_IP = "127.0.0.1";
    private static final byte[] BUFF = new byte[4096];

    private DatagramPacket packet;
    private InetAddress address;

    public static void main(String[] args) {
	Client client = new Client();
	client.start();
    }

    private void start() {
	try (DatagramSocket client = new DatagramSocket(); Scanner sc = new Scanner(System.in);) {

	    address = InetAddress.getByName(SERVER_IP);

	    int choose = -1;
	    do {
		choose = chooseHandler(sc);
		sendPacket(client, String.valueOf(choose));
		switch (choose) {
		case 0:
		    System.out.println("Ket thuc chuong trinh");
		    break;
		case 1:
		    moveFilehandle(client, sc);
		    break;
		case 2:
		    int n = enterNumber(client, sc, "Nhap N = ");
		    int m = enterNumber(client, sc, "Nhap M = ");
		    int[][] matrix = enterElements(n, m, sc);
		    sendMatrix(client, n, m, matrix);
		    String mesage = receivePacket(client);
		    System.out.println(mesage);

		    System.out.println(receivePacket(client));
		    System.out.println(receivePacket(client));
		    break;
		default:
		    System.out.println("Nhap sai. nhap lai");
		}
	    } while (choose != 0);
	} catch (SocketException | UnknownHostException e) {
	    e.printStackTrace();
	}
    }

    private int chooseHandler(Scanner sc) {
	int choose = -1;
	do {
	    System.out.println(">> Nhap Lua Chon");
	    System.out.println(">> 1. Bai 1");
	    System.out.println(">> 2. Bai 2");
	    System.out.println(">> 0. Thoat");
	    try {
		choose = Integer.parseInt(sc.nextLine());
		return choose;
	    } catch (Exception e) {
		System.out.println("Nhap sai. Nhap lai");
	    }
	} while (true);
    }

    private void moveFilehandle(DatagramSocket client, Scanner sc) {
	do {
	    System.out.print("Nhap duong dan source file: ");
	    String dirSource = sc.nextLine();
	    sendPacket(client, dirSource);

	    String isExistFile = receivePacket(client);
	    String message = receivePacket(client);
	    System.out.println(message);
	    if (isExistFile.equals("true")) {
		break;
	    }
	} while (true);

	do {
	    System.out.print("Nhap duong dan des File (chi can nhap ten thu muc): ");
	    String dirDes = sc.nextLine();
	    sendPacket(client, dirDes);
	    String check = receivePacket(client);
	    if (check.equals("true")) {
		System.out.println(receivePacket(client));
		break;
	    } else {
		System.out.println(receivePacket(client));
	    }
	} while (true);

	String message = receivePacket(client);
	System.out.println(message);

    }

    private int enterNumber(DatagramSocket client, Scanner sc, String message) {
	int n;
	do {
	    System.out.print(message);
	    try {
		n = Integer.parseInt(sc.nextLine());

		if (n > 0) {
		    break;
		} else {
		    System.out.println("Nhap sai. Nhap Lai");
		}
	    } catch (Exception e) {
		System.out.println("Nhap sai. Nhap Lai");
	    }
	} while (true);
	sendPacket(client, String.valueOf(n));
	return n;
    }

    private int[][] enterElements(int n, int m, Scanner sc) {
	int[][] matrix = new int[n][m];
	for (int i = 0; i < n; i++) {
	    for (int j = 0; j < m; j++) {
		do {
		    System.out.print("Nhap phan thu vi tri " + i + "" + j + " = ");
		    try {
			matrix[i][j] = Integer.parseInt(sc.nextLine());
			break;
		    } catch (Exception e) {
			System.out.println("Nhap sai. nhap lai");
		    }
		} while (true);
	    }
	}
	return matrix;
    }

    private void sendMatrix(DatagramSocket client, int n, int m, int[][] matrix) {
	for (int i = 0; i < n; i++) {
	    for (int j = 0; j < m; j++) {
		sendPacket(client, String.valueOf(matrix[i][j]));
	    }
	}
    }

    private void sendPacket(DatagramSocket client, String message) {
	try {
	    packet = new DatagramPacket(message.getBytes(), message.length(), address, SERVER_PORT);
	    client.send(packet);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private String receivePacket(DatagramSocket client) {
	try {
	    packet = new DatagramPacket(BUFF, BUFF.length);
	    client.receive(packet);
	    return new String(packet.getData(), 0, packet.getLength());
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return "";
    }
}
