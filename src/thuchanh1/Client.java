package thuchanh1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final String NHAP_LAI = "Nhap sai. nhap lai";
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
		System.out.println(">> Nhap lua chon");
		System.out.println(">> 1. Bai 1");
		System.out.println(">> 2. Bai 2");
		System.out.println(">> 0. Thoat");

		choose = checkChoose(sc, choose, output);
		switch (choose) {
		case 1:
		    int n;

		    // Tính tổng S = 1+3+5+7 +…+ (2n+1)
		    System.out.println("Tính tổng S = 1+3+5+7 +…+ (2n+1)");
		    n = checkNumberLargeZero(sc, output);
		    System.out.println(input.readUTF());

		    System.out.println();

		    // Tính tổng S = 1*2 + 2*3 +…+ n*(n+1)
		    System.out.println("Tính tổng S = 1*2 + 2*3 +…+ n*(n+1)");
		    n = checkNumberLargeZero(sc, output);
		    System.out.println(input.readUTF());

		    System.out.println();

		    // Tính tổng S = 1-2 + 3-4+...+ (2n+1)
		    System.out.println("Tính tổng S = 1-2 + 3-4+...+ (2n+1)");
		    n = checkNumberLargeZero(sc, output);
		    System.out.println(input.readUTF());

		    System.out.println();

		    // tinh tong 2 so a, b
		    System.out.println("Tinh Tong a,b");
		    int a = 0;
		    int b = 0;
		    a = checkNumber(sc, "a", output);
		    b = checkNumber(sc, "b", output);
		    System.out.println(input.readUTF());

		    System.out.println();

		    // Tính lập phương của 2 số a và b với a,b nhập từ client.
		    System.out.println("Tính lập phương của 2 số a và b");
		    a = checkNumber(sc, "a", output);
		    b = checkNumber(sc, "b", output);
		    System.out.println(input.readUTF());

		    System.out.println();

		    // Tính binh phương của 2 số a và b với a,b nhập từ client.
		    System.out.println("Tính bình phương của 2 số a và b");
		    a = checkNumber(sc, "a", output);
		    b = checkNumber(sc, "b", output);
		    System.out.println(input.readUTF());

		    System.out.println();

		    // Tính tích S1 = 1*2*3*…*n
		    System.out.println("Tính tích S1 = 1*2*3*…*n");
		    n = checkNumberLargeZero(sc, output);
		    System.out.println(input.readUTF());

		    System.out.println();

		    // Tinh S2= 1+2+…+n
		    System.out.println("Tinh S2= 1+2+…+n ");
		    n = checkNumberLargeZero(sc, output);
		    System.out.println(input.readUTF());

		    break;

		case 2:
		    do {
			System.out.print("Nhap duong dan source file: ");
			String src = sc.nextLine();
			output.writeUTF(src);
			if (input.readBoolean()) {
			    System.out.println("File ton tai");
			    break;
			} else {
			    System.out.println("File khong ton tai. nhap lai");
			}
		    } while (true);

		    int lc = -1;
		    String lc1 = input.readUTF();
		    String lc2 = input.readUTF();
		    String lc3 = input.readUTF();
		    do {
			System.out.println("Nhap lua chon");
			System.out.println(lc1);
			System.out.println(lc2);
			System.out.println(lc3);
			System.out.println("0. Thoat");
			lc = checkChoose(sc, lc, output);
			switch (lc) {
			case 1:
			    System.out.println(input.readUTF());
			    break;

			case 2:
			    System.out.println(input.readUTF());
			    break;

			case 3:
			    System.out.println(input.readUTF());
			    break;

			case 0:
			    System.out.println("");
			    break;

			default:
			    System.out.println(NHAP_LAI);
			}
		    } while (lc != 0);

		    break;

		case 0:
		    System.out.println("Ket thuc chuong trinh");
		    break;

		default:
		    System.out.println(NHAP_LAI);

		}
	    } while (choose != 0);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * 
     * @param sc
     * @param choose
     * @param output
     * @return choose
     */
    private static int checkChoose(Scanner sc, int choose, DataOutputStream output) {
	try {
	    choose = sc.nextInt();
	    sc.nextLine();
	    output.writeInt(choose);
	} catch (Exception e) {
	    sc.nextLine();
	}
	return choose;
    }

    private static int checkNumberLargeZero(Scanner sc, DataOutputStream output) {
	int n;
	do {
	    try {
		System.out.print("Nhap n = ");
		n = sc.nextInt();
		sc.nextLine();
		if (n > 0) {
		    output.writeInt(n);
		    return n;
		} else {
		    System.out.println(NHAP_LAI);
		}
	    } catch (Exception e) {
		System.out.println(NHAP_LAI);
		sc.nextLine();
	    }
	} while (true);
    }

    private static int checkNumber(Scanner sc, String s, DataOutputStream output) {
	int n;
	do {
	    try {
		System.out.print("Nhap " + s + " = ");
		n = sc.nextInt();
		sc.nextLine();
		output.writeInt(n);
		return n;
	    } catch (Exception e) {
		System.out.println(NHAP_LAI);
		sc.nextLine();
	    }
	} while (true);
    }
}
