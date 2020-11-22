package udp.baikiemtra1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

	private static final String SERVER_IP = "127.0.0.1";

	private static final int SERVER_PORT = 8888;

	private static final byte[] BUFF = new byte[4096];

	public static void start() {
		try (DatagramSocket client = new DatagramSocket();
				InputStreamReader streamReader = new InputStreamReader(
						System.in);
				BufferedReader reader = new BufferedReader(streamReader);) {
			DatagramPacket packet;
			InetAddress address = InetAddress.getByName(SERVER_IP);

			do {
				System.out.println("Nhập vào đường dẫn tuyệt đối của file.");
				System.out.println("VD D:\\java\\laptrinhmang\\tenfile");
				System.out.print("Đường dẫn: ");
				String pathFile = reader.readLine();
				packet = new DatagramPacket(pathFile.getBytes(),
						pathFile.length(), address, SERVER_PORT);
				client.send(packet);
				packet = new DatagramPacket(BUFF, BUFF.length);
				client.receive(packet);
				String s = new String(packet.getData(), 0, packet.getLength());
				if (s.equals("1")) {
					System.out.println("File tồn tại. Tiếp tục xử lý");
					break;
				} else if (s.equals("0")) {
					System.out.println("File không tồn tại. nhập lại");
				}
			} while (true);
			int choose = -1;
			do {

				System.out.println(">>>> Nhap lua chon <<<<");
				System.out.println(
						">>>> 1. Hien thi danh sach so nguyen to < n.");
				System.out.println(">>>> 2. Chuan Hoa chuoi.");
				System.out.println(">>>> 3. Sap xep mang 2 chieu ");
				System.out.println(">>>> 0. Thoat");
				try {
					choose = Integer.parseInt(reader.readLine());
				} catch (Exception e) {
					System.out.println(e.getMessage());
					System.out.println("Lua chon sai. Nhap lai");

				}
				packet = new DatagramPacket(String.valueOf(choose).getBytes(),
						String.valueOf(choose).length(), address, SERVER_PORT);
				client.send(packet);

				switch (choose) {
					case 1 :
						System.out.println("Cau 1");
						System.out.println("Day so nguyen to < n");
						packet = new DatagramPacket(BUFF, BUFF.length);
						client.receive(packet);
						String s = new String(packet.getData(), 0,
								packet.getLength());
						System.out.println(s);
						break;
					case 2 :
						System.out.println("Cau 2");
						System.out.println("Chuan hoa chuoi");
						packet = new DatagramPacket(BUFF, BUFF.length);
						client.receive(packet);
						String c2 = new String(packet.getData(), 0,
								packet.getLength());
						System.out.println(c2);
						break;
					case 3 :
						System.out.println("Cau 3");
						System.out.println("Sap Xep Mang 2 chieu 3x3");
						System.out.println("Sap xep theo hang");
						packet = new DatagramPacket(BUFF, BUFF.length);
						client.receive(packet);
						String c3 = new String(packet.getData(), 0,
								packet.getLength());
						System.out.println(c3);
						break;
					case 0 :
						System.out.println("ket thuc chuong trinh");
						break;
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
