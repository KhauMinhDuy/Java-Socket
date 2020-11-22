package thitracnghiem;

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
		DataInputStream input = new DataInputStream(socket.getInputStream());
		DataOutputStream output = new DataOutputStream(socket.getOutputStream());
		Scanner sc = new Scanner(System.in);) {

	    // dang nhap
	    do {
		System.out.print("Enter username: ");
		String username = sc.nextLine();
		System.out.print("Enter password: ");
		String password = sc.nextLine();

		output.writeUTF(username);
		output.writeUTF(password);

		boolean login = input.readBoolean();
		if (login) {
		    System.out.println("Login Sucess");
		    break;
		} else {
		    System.out.println("Login Fail");
		    continue;
		}
	    } while (true);

	    // thi trac nghiem
	    int stt = 1;
	    while (true) {
		int cauhoi = input.readInt();
		String trinhdo = input.readUTF();
		String noidung = input.readUTF();
		String a = input.readUTF();
		String b = input.readUTF();
		String c = input.readUTF();
		String d = input.readUTF();
		String dapan = input.readUTF();
		int count = input.readInt();
		System.out.println(stt++ + ") " + noidung);
		System.out.println("a) " + a + "\t" + "b) " + b + "\t" + "c) " + c + "\t" + "d) " + d);

		System.out.print(">> ");
		String traloi = sc.nextLine();
		output.writeUTF(traloi);

		System.out.println(">> " + input.readUTF());
		if (count == 10) {
		    int diem = input.readInt();
		    System.out.println("Diem cua ban la : " + diem);
		    if (input.readBoolean()) {
			System.out.println("Da luu diem");
		    } else {
			System.out.println("Luu diem that bai");
		    }
		    break;
		}
	    }

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
