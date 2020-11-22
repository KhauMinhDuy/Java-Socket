package thithu;

import static thithu.DAO.diemTrungBinh;
import static thithu.DAO.validate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Server {

    public static void main(String[] args) {
	Server.start();
    }

    private static void start() {
	try (ServerSocket server = new ServerSocket(61298);) {
	    System.out.println(">>>> SERVER RUNNING <<<<");
	    try (Socket socket = server.accept();
		    DataInputStream input = new DataInputStream(socket.getInputStream());
		    DataOutputStream output = new DataOutputStream(socket.getOutputStream());) {
		System.out.println(">>>> CLIENT ACCEPT <<<<");
		int choose;

		do {
		    choose = input.readInt();
		    switch (choose) {
		    case 1: {
			int number = input.readInt();
			String[] numbers = String.valueOf(number).split("");
			Stream<Integer> map = Arrays.stream(numbers).map(e -> Integer.parseInt(e));
			Integer reduce = map.reduce(0, Integer::sum);
			output.writeInt(reduce.intValue());
			break;
		    }
		    case 2: {
			String srcString = input.readUTF();
			Path srcPath = Paths.get(srcString);
			if (Files.exists(srcPath)) {
			    output.writeBoolean(true);
			} else {
			    output.writeBoolean(false);
			}

			List<String> arr = new ArrayList<>();
			BufferedReader reader = new BufferedReader(new FileReader(new File(srcString)));
			String read;
			int column = 0;
			int row = 0;
			while ((read = reader.readLine()) != null) {
			    row++;
			    String[] split = read.split(" ");
			    column = split.length;
			    Arrays.stream(split).forEach(e -> arr.add(e));
			}

			List<Integer> collect = arr.stream().map(e -> Integer.parseInt(e)).collect(Collectors.toList());
			collect.sort(Integer::compareTo);

			String desString = input.readUTF();

			BufferedWriter writer = new BufferedWriter(new PrintWriter(new File(desString)));
			int[][] arrs = new int[row][column];
			int c = 0;
			for (int i = 0; i < row; i++) {
			    for (int j = 0; j < column; j++) {
				arrs[i][j] = collect.get(c++);
			    }
			}

			for (int i = 0; i < row; i++) {
			    for (int j = 0; j < column; j++) {
				writer.write(arrs[i][j] + " ");
			    }
			    writer.write("\n");
			}
			output.writeUTF("Success");
			reader.close();
			writer.close();
			break;
		    }
		    case 3: {
			do {
			    String username = input.readUTF();
			    String password = input.readUTF();
			    boolean check = validate(username, password);
			    if (check) {
				output.writeBoolean(true);
				break;
			    } else {
				output.writeBoolean(false);
			    }
			} while (true);
			try {
			    StringBuilder buider = new StringBuilder();
			    String s1 = String.format("%-20s | %-20s | %-10s | %-20s | %-20s | %-10s", "MaSV", "Ho Lot",
				    "Ten", "Ma Lop", "Diem trung binh", "Ket qua");
			    buider.append(s1 + "\n");
			    ResultSet trungBinh = diemTrungBinh();
			    while (trungBinh.next()) {
				float diem1 = trungBinh.getFloat("DiemBT1");
				float diem2 = trungBinh.getFloat("DiemBT2");
				float diem3 = trungBinh.getFloat("DiemBT3");
				String holot = trungBinh.getString("HoLot");
				String ten = trungBinh.getString("Ten");
				String masv = trungBinh.getString("MaSV");
				String malop = trungBinh.getString("MaLop");
				float dtb = (diem1 + diem2 + diem3) / 3;
				String kq = "";
				if (dtb >= 5) {
				    kq = "D";
				} else {
				    kq = "R";
				}
				String format = String.format("%-20s | %-20s | %-10s | %-20s | %-20.1f | %-10s", masv,
					holot, ten, malop, dtb, kq);
				buider.append(format);
				buider.append("\n");
			    }

			    String rs = buider.toString();
			    output.writeUTF(rs);
			} catch (SQLException e) {
			    e.printStackTrace();
			}
			break;
		    }
		    }
		} while (choose != 0);
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
