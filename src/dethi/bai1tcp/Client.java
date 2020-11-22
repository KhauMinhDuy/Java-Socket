package dethi.bai1tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
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
	    int[] numbers = new int[4];
	    System.out.println("Nhap vao 4 so");
	    for (int i = 0; i < 4; i++) {
		try {
		    System.out.print("Nhap so thu " + (i + 1) + ": ");
		    numbers[i] = sc.nextInt();
		    sc.nextLine();
		    output.writeInt(numbers[i]);
		} catch (Exception e) {
		    i--;
		    System.out.println("So khong hop le. nhap lai");
		    sc.nextLine();
		}
	    }

	    for (int i = 0; i < 4; i++) {
		numbers[i] = input.readInt();
	    }

	    System.out.println("Danh sach so da sap xep");
	    Arrays.stream(numbers).forEach(System.out::println);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
