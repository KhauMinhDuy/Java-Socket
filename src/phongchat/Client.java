package phongchat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 61298;

    public static void main(String[] args) {
	Client.start();
    }

    private static void start() {
	try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
		DataOutputStream output = new DataOutputStream(socket.getOutputStream());
		DataInputStream input = new DataInputStream(socket.getInputStream());
		Scanner sc = new Scanner(System.in);) {
	    String username;
	    do {
		System.out.print("Nhap username: ");
		username = sc.nextLine();
		System.out.print("Nhap password: ");
		String password = sc.nextLine();

		output.writeUTF(username);
		output.writeUTF(password);

		boolean validate = input.readBoolean();
		if (validate) {
		    System.out.println("Dang Nhap Thanh Cong");
		    break;
		} else {
		    System.out.println("Dang Nhap That Bai");
		}

	    } while (true);

	    int choose = -1;
	    do {
		System.out.println(">> Nhap Lua Chon");
		System.out.println(">> 1. Tim Phong Chat");
		System.out.println(">> 2. Tao Phong Chat");
		System.out.println(">> 0. Thoat");
		try {
		    choose = sc.nextInt();
		    sc.nextLine();
		    output.writeInt(choose);
		} catch (Exception e) {
		    sc.nextLine();
		    System.out.println("Nhap sai. nhap lai");
		    continue;
		}
		switch (choose) {
		case 1:
		    System.out.print("Nhap Ma Phong: ");
		    String maPhong = sc.nextLine();
		    output.writeUTF(maPhong);
		    System.out.println(input.readUTF());
		    System.out.println(input.readUTF());
		    String exit = "";
		    while (exit.equals("exit")) {
			exit = sc.nextLine();

		    }
		    break;
		case 2:

		    break;
		default:
		    System.out.println("Nhap sai. Nhap lai");
		}

	    } while (choose != 0);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
