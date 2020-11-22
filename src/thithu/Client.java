package thithu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final int SERVER_PORT = 61298;
    private static final String SERVER_IP = "127.0.0.1";

    public static void main(String[] args) {
	Client.start();
    }

    private static void start() {
	try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
		DataInputStream input = new DataInputStream(socket.getInputStream());
		DataOutputStream output = new DataOutputStream(socket.getOutputStream());
		Scanner sc = new Scanner(System.in);) {

	    int choose = -1;
	    do {
		System.out.println("Nhap lua chon");
		System.out.println("1. Cau 1");
		System.out.println("2. Cau 2");
		System.out.println("3. Cau 3");
		System.out.println("0. Thoat");
		try {
		    choose = sc.nextInt();
		    sc.nextLine();
		    output.writeInt(choose);
		} catch (Exception e) {
		    System.out.println("Lua chon sai. nhap lai");
		    sc.nextLine();
		    continue;
		}
		switch (choose) {
		case 1: {
		    System.out.print("Nhap so bat ki: ");
		    int number;
		    do {
			try {
			    number = sc.nextInt();
			    sc.nextLine();
			    output.writeInt(number);
			    break;

			} catch (Exception e) {
			    System.out.println("Nhap sai. nhap lai");
			    sc.nextLine();
			}
		    } while (true);

		    System.out.println("Tong cac so trong " + number + " la: " + input.readInt());
		    break;
		}
		case 2: {
		    System.out.print("nhap Source path file: ");
		    String srcPath = sc.nextLine();
		    output.writeUTF(srcPath);

		    boolean checkFileExist = input.readBoolean();
		    if (checkFileExist) {
			System.out.println("File ton tai");
		    } else {
			System.out.println("File khong ton tai");
		    }

		    System.out.print("Nhap Des Path File:");
		    String desString = sc.nextLine();
		    output.writeUTF(desString);

		    System.out.println(input.readUTF());
		    break;
		}

		case 3: {
		    do {
			System.out.print("Nhap username: ");
			String username = sc.nextLine();
			System.out.print("Nhap password: ");
			String password = sc.nextLine();
			output.writeUTF(username);
			output.writeUTF(password);
			boolean check = input.readBoolean();
			if (check) {
			    System.out.println("Dang nhap thanh cong");
			    break;
			} else {
			    System.out.println("Dang nhap that bai");
			}
		    } while (true);
		    System.out.println(input.readUTF());
		    break;
		}
		case 0: {
		    System.out.println("Ket thuc chuong trinh");
		    break;
		}
		default: {
		    System.out.println("Nhap sai. nhap lai");
		}
		}
	    } while (choose != 0);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
