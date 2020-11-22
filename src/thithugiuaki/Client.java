package thithugiuaki;

import static thithugiuaki.Regex.checkValidatePassword;

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

	    int choose;
	    do {
		choose = chooseHandle(sc);
		sendPacket(client, String.valueOf(choose));
		switch (choose) {
		case 0:
		    System.out.println("Ket Thuc Chuong Trinh");
		    break;
		case 1:
		    loginHandle(client, sc);
		    checkFile(client, sc);
		    String msg = receivePacket(client);
		    System.out.println(msg);
		    break;
		case 2:
		    registerHandle(client, sc);
		    break;
		default:
		    System.out.println("Nhap sai. Nhap Lai");
		}
	    } while (choose != 0);

	} catch (SocketException | UnknownHostException e) {
	    e.printStackTrace();
	}
    }

    private int chooseHandle(Scanner sc) {
	int choose = -1;
	do {
	    System.out.println(">> Nhap lua chon");
	    System.out.println(">> 1. Dang Nhap");
	    System.out.println(">> 2. Tao Tai Khoan");
	    System.out.println(">> 0. Thoat");
	    try {
		choose = Integer.parseInt(sc.nextLine());
		return choose;
	    } catch (Exception e) {
		System.out.println("Nhap sai. Nhap lai");
	    }
	} while (true);
    }

    private void loginHandle(DatagramSocket client, Scanner sc) {

	do {
	    System.out.print("Nhap Username: ");
	    String username = sc.nextLine();
	    System.out.print("Nhap Password: ");
	    String password = sc.nextLine();

	    sendPacket(client, username);
	    sendPacket(client, password);
	    String validate = receivePacket(client);
	    System.out.println(receivePacket(client));
	    if (validate.equals("true")) {
		break;
	    }
	} while (true);
    }

    private void checkFile(DatagramSocket client, Scanner sc) {
	do {
	    System.out.print("Nhap duong dan file: ");
	    String dirFile = sc.nextLine();
	    sendPacket(client, dirFile);

	    String check = receivePacket(client);
	    System.out.println(receivePacket(client));
	    if (check.equals("true")) {
		break;
	    }
	} while (true);

	System.out.print("Nhap vi tri dau (vd: 00):  ");
	String viTriDau = sc.nextLine();
	System.out.print("Nhap vi tri cuoi (vd: 44): ");
	String vitricuoi = sc.nextLine();
	sendPacket(client, viTriDau);
	sendPacket(client, vitricuoi);

    }

    private void registerHandle(DatagramSocket client, Scanner sc) {
	do {
	    System.out.print("Nhap Username: ");
	    String username = sc.nextLine();
	    System.out.print("Nhap Password: ");
	    String password = sc.nextLine();
	    if (checkValidatePassword(password)) {
		sendPacket(client, username);
		sendPacket(client, password);
		int checkInsertUser = Integer.parseInt(receivePacket(client));
		System.out.println(receivePacket(client));
		if (checkInsertUser == 0) {
		    break;
		}
	    } else {
		System.out.println("Password khong dung dinh dang");
	    }
	} while (true);

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
