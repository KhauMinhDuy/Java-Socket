import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final int SERVER_PORT = 8888;
    private static final String SERVER_IP = "127.0.0.1";

    public static void start() {
	try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
		DataInputStream input = new DataInputStream(socket.getInputStream());
		DataOutputStream output = new DataOutputStream(socket.getOutputStream());
		Scanner sc = new Scanner(System.in);) {
	    int choose = -1;
	    int a, b, c, d;
	    do {
		System.out.println("Nhap Lua Chon");
		System.out.println(">> 1. Bai1");
		System.out.println(">> 2. Bai2");
		System.out.println(">> 0. Thoat");
		try {
		    choose = sc.nextInt();
		    output.writeInt(choose);
		    sc.nextLine();
		} catch (Exception e) {
		    System.out.println("Nhap sai. Nhap lai");
		    sc.nextLine();
		    continue;
		}

		switch (choose) {
		case 1:
		    System.out.println("Giai phuong trinh bac 3");
		    do {
			System.out.print("Nhap a = ");
			try {
			    a = sc.nextInt();
			    output.writeInt(a);
			    break;
			} catch (Exception e) {
			    System.out.println("Nhap so a sai. nhap lai");
			    sc.nextLine();
			}
		    } while (true);

		    do {
			System.out.print("Nhap b = ");
			try {
			    b = sc.nextInt();
			    output.writeInt(b);
			    break;
			} catch (Exception e) {
			    System.out.println("Nhap so b sai. nhap lai");
			    sc.nextLine();
			}
		    } while (true);

		    do {
			System.out.print("Nhap c = ");
			try {
			    c = sc.nextInt();
			    output.writeInt(c);
			    break;
			} catch (Exception e) {
			    System.out.println("Nhap so c sai. nhap lai");
			    sc.nextLine();
			}
		    } while (true);

		    do {
			System.out.print("Nhap d = ");
			try {
			    d = sc.nextInt();
			    output.writeInt(d);
			    break;
			} catch (Exception e) {
			    System.out.println("Nhap so d sai. nhap lai");
			    sc.nextLine();
			}
		    } while (true);

		    double m = input.readDouble();
		    if (m >= 0) {
			System.out.println("Phuong trinh co nghiem la" + input.readDouble());
		    } else {
			System.out.println(
				"Phuong trinh co 2 nghiem la: " + input.readDouble() + " va " + input.readDouble());
		    }

		    break;
		case 2:

		    System.out.println("Copy folder va file");
		    System.out.println("Cau 2. Kho qua");

		    do {
			System.out.println("Nhap duong dan cua file muon copy:");
			output.writeUTF(sc.nextLine());
			boolean check = input.readBoolean();
			if (check) {
			    break;
			} else {
			    continue;
			}
		    } while (true);

		    System.out.println("Nhap duong dan va ten file muon paste:");
		    output.writeUTF(sc.nextLine());
		    String readUTF = input.readUTF();
		    System.out.println(readUTF);
		    break;
		case 0:
		    System.out.println("Ket thuc chuong trinh");
		default:
		    System.out.println("Nhap sai. nhap lai");
		}
	    } while (choose != 0);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public static void main(String[] args) {
	Client.start();
    }

}
