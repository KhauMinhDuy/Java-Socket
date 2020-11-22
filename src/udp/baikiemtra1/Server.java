package udp.baikiemtra1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

public class Server {

    private static final int SERVER_PORT = 8888;

    private static final byte[] BUFF = new byte[4096];

    public static void start() {
	try (DatagramSocket server = new DatagramSocket(SERVER_PORT);) {
	    System.out.println(">>>> SERVER RUNNING <<<<");
	    DatagramPacket packet;
	    File pathFile;
	    do {
		packet = new DatagramPacket(BUFF, BUFF.length);
		server.receive(packet);
		String s = new String(packet.getData(), 0, packet.getLength());
		pathFile = new File(s);
		if (pathFile.exists()) {

		    String temp = "1";
		    packet = new DatagramPacket(temp.getBytes(), temp.length(), packet.getAddress(), packet.getPort());
		    server.send(packet);
		    break;
		} else {
		    String temp = "0";
		    packet = new DatagramPacket(temp.getBytes(), temp.length(), packet.getAddress(), packet.getPort());
		    server.send(packet);
		}
	    } while (true);

	    ArrayList<String> arrayList = new ArrayList<>();
	    try (FileReader fileInputStream = new FileReader(pathFile);
		    BufferedReader reader = new BufferedReader(fileInputStream);) {
		String s;
		while ((s = reader.readLine()) != null) {
		    arrayList.add(s);
		}
	    }

	    String rv;
	    do {
		packet = new DatagramPacket(BUFF, BUFF.length);
		server.receive(packet);
		rv = new String(packet.getData(), 0, packet.getLength());
		switch (Integer.parseInt(rv)) {
		case 1:
		    String listPrimeNumber = listPrimeNumber(Integer.parseInt(arrayList.get(0)));
		    packet = new DatagramPacket(listPrimeNumber.getBytes(), listPrimeNumber.length(),
			    packet.getAddress(), packet.getPort());
		    server.send(packet);
		    break;
		case 2:
		    String chuanHoa = chuanHoa(arrayList.get(1));
		    packet = new DatagramPacket(chuanHoa.getBytes(), chuanHoa.length(), packet.getAddress(),
			    packet.getPort());
		    server.send(packet);
		    break;
		case 3:
		    String sortArr = sortArr(arrayList.get(2));
		    packet = new DatagramPacket(sortArr.getBytes(), sortArr.length(), packet.getAddress(),
			    packet.getPort());
		    server.send(packet);
		    break;

		}
	    } while (Integer.parseInt(rv) != 0);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private static String listPrimeNumber(int n) {
	StringBuilder s = new StringBuilder();
	for (int i = 2; i <= n; i++) {
	    if (isPrime(i)) {
		s.append(i + " ");
	    }
	}
	return s.toString();
    }

    private static boolean isPrime(int n) {
	if (n < 2)
	    return false;
	for (int i = 2; i <= Math.sqrt(n); i++) {
	    if (n % i == 0)
		return false;
	}
	return true;
    }

    private static String chuanHoa(String s) {
	String[] arr = s.trim().split("\\s+");
	StringBuilder builder = new StringBuilder();
	for (String v : arr) {
	    if (v.length() >= 2) {
		builder.append(v.substring(0, 1).toUpperCase() + v.substring(1, v.length()) + " ");
	    } else {
		builder.append(v + " ");
	    }
	}

	return builder.toString();
    }

    private static String sortArr(String s) {
	String[] arrTemp = s.trim().split("\\s+");
	ArrayList<Integer> arr = new ArrayList<Integer>();
	for (int i = 0; i < arrTemp.length; i++) {
	    arr.add(Integer.parseInt(arrTemp[i]));
	}

	arr.sort(Integer::compareTo);
	StringBuilder builder = new StringBuilder();
	int[][] result = new int[3][3];
	int c = 0;
	for (int i = 0; i < 3; i++) {
	    for (int j = 0; j < 3; j++) {
		result[i][j] = arr.get(c++);
	    }
	}

	for (int i = 0; i < 3; i++) {
	    for (int j = 0; j < 3; j++) {
		builder.append(result[i][j] + " | ");
	    }
	    builder.append("\n");
	}
	return builder.toString();
    }

    public static void main(String[] args) {
	Server.start();
    }

}
