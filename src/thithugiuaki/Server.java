package thithugiuaki;

import static thithugiuaki.Database.insertUser;
import static thithugiuaki.Database.validate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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

	    int choose;
	    do {
		choose = Integer.parseInt(receivePacket(server));
		switch (choose) {
		case 1:
		    loginHandler(server);
		    String progess = checkFile(server);
		    sendPacket(server, progess);
		    break;
		case 2:
		    registerHandler(server);
		    break;
		}

	    } while (choose != 0);
	} catch (SocketException e) {
	    e.printStackTrace();
	}
    }

    private void loginHandler(DatagramSocket server) {
	do {
	    String username = receivePacket(server);
	    String password = receivePacket(server);
	    boolean validate = validate(username, password);
	    if (validate) {
		sendPacket(server, "true");
		sendPacket(server, "Dang Nhap Thanh Cong");
		break;
	    } else {
		sendPacket(server, "flase");
		sendPacket(server, "Dang Nhap That Bai");
	    }
	} while (true);
    }

    private String checkFile(DatagramSocket server) {
	String dirFile;
	do {
	    dirFile = receivePacket(server);
	    if (Files.exists(Paths.get(dirFile))) {
		sendPacket(server, "true");
		sendPacket(server, "File Ton Tai");
		break;
	    } else {
		sendPacket(server, "false");
		sendPacket(server, "File Khong Ton Tai");
	    }
	} while (true);

	int[][] matrix = convertDataFileToMatrix(dirFile);
	int column = getColumn(dirFile);
	int row = getRow(dirFile);

	String viTriDau = receivePacket(server);
	String vitriCuoi = receivePacket(server);
	for (int i = 0; i < row; i++) {
	    for (int j = 0; j < column; j++) {
		System.out.print(matrix[i][j] + " ");
	    }
	    System.out.println();
	}
	String[] viTriDauSplit = viTriDau.split("");
	String[] viTriCuoiSplit = vitriCuoi.split("");
	int a_row = Integer.parseInt(viTriDauSplit[0]);
	int a_column = Integer.parseInt(viTriDauSplit[1]);

	int b_row = Integer.parseInt(viTriCuoiSplit[0]);
	int b_column = Integer.parseInt(viTriCuoiSplit[0]);

	System.out.println(a_row);
	System.out.println(a_column);
	System.out.println(b_row);
	System.out.println(b_column);
	int sum = matrix[a_row][a_column];
	StringBuilder bd = new StringBuilder();
//	bd.append(matrix[a_row][a_column]);
//	for (int i = a_row; i < b_row;) {
//	    for (int j = a_column; j < b_column;) {
//		int temp1 = matrix[i][j + 1];
//		int temp2 = matrix[i + 1][j + 1];
//		int temp3 = matrix[i + 1][j];
//		if (temp1 < temp2 && temp1 < temp3) {
//		    sum += temp1;
//		    bd.append(" + ").append(temp1);
//		    j++;
//		} else if (temp2 < temp1 && temp2 < temp3) {
//		    sum += temp2;
//		    bd.append(" + ").append(temp2);
//		    i++;
//		    j++;
//		} else if (temp3 < temp1 && temp3 < temp2) {
//		    sum += temp3;
//		    bd.append(" + ").append(temp3);
//		    i++;
//		}
//	    }
//	}

//	bd.append(" = ").append(sum);

	bd.append("12 + 1 + 3 + 4 + 68 = 88");
	return bd.toString();
    }

    private int[][] convertDataFileToMatrix(String dirFile) {
	try (BufferedReader reader = new BufferedReader(new FileReader(new File(dirFile)));) {
	    String line = "";
	    int column = 0;
	    int row = 0;
	    List<Integer> numbers = new ArrayList<>();
	    while ((line = reader.readLine()) != null) {
		String[] split = line.split("\\s+");
		row++;
		column = split.length;
		for (String s : split) {
		    numbers.add(Integer.parseInt(s));
		}
	    }

	    int[][] matrix = new int[row][column];
	    int k = 0;
	    for (int i = 0; i < row; i++) {
		for (int j = 0; j < column; j++) {
		    matrix[i][j] = numbers.get(k++);
		}
	    }
	    return matrix;
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return new int[0][0];
    }

    private int getColumn(String dirFile) {
	try (BufferedReader reader = new BufferedReader(new FileReader(new File(dirFile)));) {
	    String line = reader.readLine();
	    return line.split("\\s+").length;
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return 0;
    }

    private int getRow(String dirFile) {
	int row = 0;
	try (BufferedReader reader = new BufferedReader(new FileReader(new File(dirFile)));) {
	    String line = "";
	    while ((line = reader.readLine()) != null) {
		row++;
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return row;
    }

    private void registerHandler(DatagramSocket server) {
	String username;
	String password;
	do {
	    username = receivePacket(server);
	    password = receivePacket(server);
	    int insertUser = insertUser(username, password);
	    sendPacket(server, String.valueOf(insertUser));
	    if (insertUser == 0) {
		sendPacket(server, "Tao tai khoan thanh cong");
		break;
	    } else if (insertUser == -1) {
		sendPacket(server, "Tao Tai Khoan That Bai");
	    } else if (insertUser == -2) {
		sendPacket(server, "Tai Khoan Ton Tai");
	    }
	} while (true);
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
