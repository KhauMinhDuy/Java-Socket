package thuchanh2;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class Server {

    private static final int SERVER_PORT = 61298;
    private static final byte[] BUFF = new byte[4096];

    private DatagramPacket packet;

    public static void main(String[] args) {
	Server server = new Server();
	server.start();
    }

    private void start() {
	try (DatagramSocket server = new DatagramSocket(SERVER_PORT);) {
	    System.out.println(">>>> SERVER RUNNING <<<<");

	    int choose = -1;
	    do {
		choose = Integer.parseInt(receivePacket(server));
		switch (choose) {
		case 1:
		    moveFileHandler(server);
		    break;
		case 2:
		    int n = Integer.parseInt(receivePacket(server));
		    int m = Integer.parseInt(receivePacket(server));
		    int[][] matrix = new int[n][m];
		    receiveMatrix(server, n, m, matrix);
		    LinkedHashSet<Integer> twoNumberLess = findTwoNumberLess(n, m, matrix);

		    Object[] array = twoNumberLess.toArray();

		    StringBuilder builder = new StringBuilder();
		    for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
			    if (matrix[i][j] == (int) array[0]) {
				builder.append("So lon nhat la " + (int) array[0] + " co vi tri la: " + i + " va " + j);
				builder.append("\n");
			    } else if (matrix[i][j] == (int) array[1]) {
				builder.append(
					"So lon thu hai la " + (int) array[1] + " co vi tri la: " + i + " va " + j);
				builder.append("\n");
			    }
			}
		    }

		    sendPacket(server, builder.toString());

		    List<Integer> numberPrimes = new ArrayList<>();
		    for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
			    if (isPrimeNumber(matrix[i][j])) {
				numberPrimes.add(matrix[i][j]);
			    }
			}
		    }

		    sendPacket(server, "Cac so nguyen to trong ma tran: " + numberPrimes.toString());
		    int sum = numberPrimes.stream().mapToInt(e -> e).sum();
		    sendPacket(server, "Tong cac so nguyen to la: " + sum);
		    break;
		default:
		    break;
		}
	    } while (choose != 0);

	} catch (SocketException e) {
	    e.printStackTrace();
	}
    }

    private LinkedHashSet<Integer> findTwoNumberLess(int n, int m, int[][] matrix) {
	List<Integer> numbers = new ArrayList<>();
	for (int i = 0; i < n; i++) {
	    for (int j = 0; j < m; j++) {
		numbers.add(matrix[i][j]);
	    }
	}

	numbers.sort(Integer::compareTo);
	LinkedHashSet<Integer> twoNumberLess = new LinkedHashSet<>();
	for (int i = numbers.size() - 1; i >= 0; i--) {
	    twoNumberLess.add(numbers.get(i));
	    if (twoNumberLess.size() == 2) {
		break;
	    }
	}
	return twoNumberLess;
    }

    private void moveFileHandler(DatagramSocket server) {
	String dirSource;
	File fileSrc;
	do {
	    dirSource = receivePacket(server);
	    fileSrc = new File(dirSource);
	    if (fileSrc.isFile()) {
		sendPacket(server, "true");
		sendPacket(server, "File Ton tai");
		break;
	    } else {
		sendPacket(server, "false");
		sendPacket(server, "File Khong Ton Tai. Nhap Lai");
	    }
	} while (true);

	String dirDes;
	File fileDes;
	do {
	    dirDes = receivePacket(server);
	    fileDes = new File(dirDes);
	    if (fileDes.isDirectory()) {
		sendPacket(server, "true");
		sendPacket(server, "Thu muc ton tai");
		break;
	    } else {
		sendPacket(server, "false");
		sendPacket(server, "Thu muc khong ton tai");
	    }
	} while (true);
	try {
	    Path move = Files.move(Paths.get(dirSource), Paths.get(dirDes + "\\" + fileSrc.getName()),
		    StandardCopyOption.REPLACE_EXISTING);
	    if (Files.exists(move)) {
		sendPacket(server, "Di Chuyen File Thanh Cong");
	    } else {
		sendPacket(server, "Di Chuyen File That Bai");
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private void receiveMatrix(DatagramSocket server, int n, int m, int[][] matrix) {
	for (int i = 0; i < n; i++) {
	    for (int j = 0; j < m; j++) {
		matrix[i][j] = Integer.parseInt(receivePacket(server));
	    }
	}
    }

    private boolean isPrimeNumber(int n) {
	if (n < 2)
	    return false;
	for (int i = 2; i <= Math.sqrt(n); i++) {
	    if (n % i == 0) {
		return false;
	    }
	}
	return true;
    }

    private void sendPacket(DatagramSocket server, String message) {
	try {
	    packet = new DatagramPacket(message.getBytes(), message.length(), packet.getAddress(), packet.getPort());
	    server.send(packet);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private String receivePacket(DatagramSocket server) {
	try {
	    packet = new DatagramPacket(BUFF, BUFF.length);
	    server.receive(packet);
	    return new String(packet.getData(), 0, packet.getLength());
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return "";
    }
}
