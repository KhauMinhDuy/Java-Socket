package csdl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static DataInputStream input;
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 61298;

    public static void main(String[] args) {
	Client.start();
    }

    private static void start() {
	try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);

		DataOutputStream output = new DataOutputStream(socket.getOutputStream());
		Scanner sc = new Scanner(System.in);) {
	    input = new DataInputStream(socket.getInputStream());
	    int choose = -1;
	    do {
		System.out.println("Nhap Lua Chon");
		System.out.println("1. Them");
		System.out.println("2. Sua");
		System.out.println("3. Xoa");
		System.out.println("0. Thoat");
		try {
		    choose = sc.nextInt();
		    sc.nextLine();
		    output.writeInt(choose);
		} catch (Exception e) {
		    System.out.println(">>>> Nhap sai. Nhap lai\n");
		    sc.nextLine();
		    continue;
		}

		switch (choose) {
		case 1: {
		    System.out.print("Nhap ma: ");
		    String ma = sc.nextLine();
		    System.out.print("Nhap ten: ");
		    String ten = sc.nextLine();
		    System.out.print("Nhap chuc vu: ");
		    String chucvu = sc.nextLine();

		    output.writeUTF(ma);
		    output.writeUTF(ten);
		    output.writeUTF(chucvu);
		    System.out.println(input.readUTF());
		    break;
		}
		case 2: {
		    System.out.print("Nhap ma: ");
		    String ma = sc.nextLine();
		    System.out.print("Nhap ten: ");
		    String ten = sc.nextLine();
		    System.out.print("Nhap chuc vu: ");
		    String chucvu = sc.nextLine();

		    output.writeUTF(ma);
		    output.writeUTF(ten);
		    output.writeUTF(chucvu);
		    System.out.println(input.readUTF());
		    break;
		}
		case 3: {
		    System.out.print("Nhap ma: ");
		    String ma = sc.nextLine();

		    output.writeUTF(ma);

		    System.out.println(input.readUTF());
		    break;
		}
		case 0: {
		    break;
		}
		default: {
		    System.out.println(">>>> Nhap sai. nhap lai\n");
		}
		}
	    } while (choose != 0);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
