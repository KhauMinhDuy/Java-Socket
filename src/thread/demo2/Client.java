package thread.demo2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
	try {
	    Socket socket = new Socket("127.0.0.1", 61298);
	    DataInputStream input = new DataInputStream(socket.getInputStream());
	    DataOutputStream output = new DataOutputStream(socket.getOutputStream());
	    Scanner sc = new Scanner(System.in);
	    do {
		System.out.print("Nhap a = ");
		try {
		    int a = Integer.parseInt(sc.nextLine());
		    output.writeInt(a);
		    break;
		} catch (Exception e) {
		    System.out.println("Nhap a sai. nhap lai");
		}
	    } while (true);
	    do {
		System.out.print("Nhap b = ");
		try {
		    int b = Integer.parseInt(sc.nextLine());
		    output.writeInt(b);
		    break;
		} catch (Exception e) {
		    System.out.println("Nhap b sai. nhap lai");
		}
	    } while (true);

	    int readInt = input.readInt();
	    System.out.println(readInt);
	} catch (

	IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}
