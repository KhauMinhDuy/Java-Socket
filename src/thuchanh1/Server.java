package thuchanh1;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Server {

    private static final int SERVER_PORT = 61298;

    public static void main(String[] args) {
	Server.start();
    }

    private static void start() {
	try (ServerSocket server = new ServerSocket(SERVER_PORT);) {
	    System.out.println(">>>> SERVER RUNNING <<<<");
	    try (Socket socket = server.accept();
		    DataInputStream input = new DataInputStream(socket.getInputStream());
		    DataOutputStream output = new DataOutputStream(socket.getOutputStream());) {
		System.out.println(">>>> CLIENT ACCEPT <<<<");
		int choose = -1;
		do {
		    choose = input.readInt();
		    switch (choose) {
		    case 1:
			// Tính tổng S = 1+3+5+7 +…+ (2n+1)
			int n1 = input.readInt();
			int sum1 = 0;
			StringBuilder b1 = new StringBuilder();
			for (int i = 0; i <= n1; i++) {
			    sum1 += (2 * i + 1);
			    b1.append((2 * i + 1));
			    b1.append(" + ");
			}

			String rs1 = b1.toString().substring(0, b1.length() - 3) + " = " + sum1;
			output.writeUTF(rs1);

			// Tính tổng S = 1*2 + 2*3 +…+ n*(n+1)
			int n2 = input.readInt();
			int sum2 = 0;
			StringBuilder b2 = new StringBuilder();
			for (int i = 1; i <= n2; i++) {
			    sum2 += (i * (i + 1));
			    b2.append(i);
			    b2.append("*");
			    b2.append(i + 1);
			    b2.append(" + ");
			}
			String rs2 = b2.toString().substring(0, b2.length() - 3) + " = " + sum2;
			output.writeUTF(rs2);

			// Tính tổng S = 1-2 + 3-4+...+ (2n+1)
			int n3 = input.readInt();
			int sum3 = 0;
			StringBuilder b3 = new StringBuilder();
			for (int i = 1; i <= n3; i++) {
			    if (i % 2 == 0) {
				sum3 -= i;
				b3.append(i).append("+");
			    } else {
				b3.append(i).append("-");
				sum3 += i;
			    }
			}
			String rs3 = b3.toString().substring(0, b3.length() - 1) + " = " + sum3;
			output.writeUTF(rs3);

			// tinh tong a,b
			int a = input.readInt();
			int b = input.readInt();
			String rs4 = a + " + " + b + " = " + (a + b);
			output.writeUTF(rs4);

			// Tính lập phương của 2 số a và b với a,b nhập từ client.
			int a_3 = input.readInt();
			int b_3 = input.readInt();
			double pow_a = Math.pow(a_3, 3);
			double pow_b = Math.pow(b_3, 3);
			String rs5 = "Lap phuong cua " + a_3 + " la: " + pow_a + "\n" + "Lap phuong cua " + b_3
				+ " la: " + pow_b;
			output.writeUTF(rs5);

			// Tính binh phương của 2 số a và b với a,b nhập từ client.
			int a_2 = input.readInt();
			int b_2 = input.readInt();
			double pow_a_2 = Math.pow(a_2, 2);
			double pow_b_2 = Math.pow(b_2, 2);
			String rs6 = "Binh phuong cua " + a_2 + " la: " + pow_a_2 + "\n" + "Binh phuong cua " + b_2
				+ " la: " + pow_b_2;
			output.writeUTF(rs6);

			// // Tính tích S1 = 1*2*3*…*n
			int n7 = input.readInt();
			int sum7 = 1;
			StringBuilder b7 = new StringBuilder();
			for (int i = 1; i <= n7; i++) {
			    b7.append(i).append("*");
			    sum7 *= i;
			}
			String rs7 = b7.toString().substring(0, b7.length() - 1) + " = " + sum7;
			output.writeUTF(rs7);

			int n8 = input.readInt();
			int sum8 = 0;
			StringBuilder b8 = new StringBuilder();
			for (int i = 1; i <= n8; i++) {
			    b8.append(i).append("+");
			    sum8 += i;
			}
			String rs8 = b8.toString().substring(0, b8.length() - 1) + " = " + sum8;
			output.writeUTF(rs8);
			break;

		    case 2:
			String srcString;
			do {
			    srcString = input.readUTF();
			    if (Files.exists(Paths.get(srcString))) {
				output.writeBoolean(true);
				break;
			    } else {
				output.writeBoolean(false);
			    }
			} while (true);

			BufferedReader reader = new BufferedReader(new FileReader(new File(srcString)));
			String line = null;
			String[] strings = null;
			while ((line = reader.readLine()) != null) {
			    strings = line.split(",");
			}

			int lc;
			output.writeUTF(strings[0]);
			output.writeUTF(strings[1]);
			output.writeUTF(strings[2]);
			do {
			    lc = input.readInt();
			    switch (lc) {
			    case 1:
				getAllPrimeNumber(output, strings[3]);
				break;

			    case 2:
				standardString(output, strings[4]);
				break;
			    case 3:
				sortNumberArray(strings[5], output);
				break;

			    default:
			    }

			} while (lc != 0);
			break;

		    }
		} while (choose != 0);
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private static void getAllPrimeNumber(DataOutputStream output, String s) throws IOException {
	int number = Integer.parseInt(s);
	int sum = 0;
	for (int i = 1; i <= number; i++) {
	    if (isPrimeNumber(i)) {
		sum += i;
	    }
	}
	String rs = "Tong cua cac so nguyen to nho hon " + number + " la " + sum;
	output.writeUTF(rs);
    }

    private static void standardString(DataOutputStream output, String strings) throws IOException {
	String s = strings.substring(1, strings.length() - 1);
	String[] wordSplit = s.trim().split("\\s+");
	StringBuilder builder = new StringBuilder();
	for (String v : wordSplit) {
	    if (v.length() >= 2) {
		builder.append(v.substring(0, 1).toUpperCase() + v.substring(1, v.length()) + " ");
	    } else {
		builder.append(v + " ");
	    }
	}
	output.writeUTF(builder.toString());
    }

    private static void sortNumberArray(String s, DataOutputStream output) throws IOException {
	String substring = s.substring(1, s.length() - 1);
	String[] numberSplit = substring.split(" ");
	int[] arr = new int[numberSplit.length];
	for (int i = 0; i < numberSplit.length; i++) {
	    arr[i] = Integer.parseInt(numberSplit[i]);
	}
	Arrays.sort(arr);
	output.writeUTF(Arrays.toString(arr));
    }

    private static boolean isPrimeNumber(int n) {
	if (n == 0 || n == 1) {
	    return false;
	}
	for (int i = 2; i <= Math.sqrt(n); i++) {
	    if (n % i == 0) {
		return false;
	    }
	}
	return true;
    }

}
