package thitracnghiem;

import static thitracnghiem.Database.checkLogin;
import static thitracnghiem.Database.getAllBoDe;
import static thitracnghiem.Database.getAllRowBoDe;
import static thitracnghiem.Database.getMaSV;
import static thitracnghiem.Database.insertDiem;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;

public class Server {

    private static final int SERVER_PORT = 61298;

    public static void main(String[] args) {
	Server.start();
    }

    private static void start() {
	try (ServerSocket server = new ServerSocket(61298);
		Socket socket = server.accept();
		DataInputStream input = new DataInputStream(socket.getInputStream());
		DataOutputStream output = new DataOutputStream(socket.getOutputStream());) {
	    System.out.println(">>>> SERVER RUNNING <<<<");
	    System.out.println(">>>> CLIENT KET NOI <<<<");

	    // kiem tra dang nhap
	    String masv;
	    do {
		String username = input.readUTF();
		String password = input.readUTF();
		if (checkLogin(username, password)) {
		    output.writeBoolean(true);
		    masv = getMaSV(username);
		    break;
		} else {
		    output.writeBoolean(false);
		}
	    } while (true);

	    // thi trac nghiem
	    try {
		ResultSet allBoDe = getAllBoDe();
		int allrows = getAllRowBoDe();
		Random rd = new Random();
		int count = 0;
		int diem = 0;
		int randomRow = rd.nextInt(allrows);
		while (allBoDe.absolute(randomRow)) {
		    randomRow = rd.nextInt(allrows);
		    output.writeInt(allBoDe.getInt("CAUHOI"));
		    output.writeUTF(allBoDe.getString("TRINHDO"));
		    output.writeUTF(allBoDe.getString("NOIDUNG"));
		    output.writeUTF(allBoDe.getString("A"));
		    output.writeUTF(allBoDe.getString("B"));
		    output.writeUTF(allBoDe.getString("C"));
		    output.writeUTF(allBoDe.getString("D"));
		    output.writeUTF(allBoDe.getString("DAP_AN"));
		    count++;
		    output.writeInt(count);

		    String client_traloi = input.readUTF();
		    if (client_traloi.equalsIgnoreCase(allBoDe.getString("DAP_AN"))) {
			output.writeUTF("DUNG");
			diem++;
		    } else {
			output.writeUTF("Sai");
		    }

		    if (count == 10) {
			output.writeInt(diem);
			String date = LocalDate.now().toString();
			boolean insertDiem = insertDiem(masv, 1, date, diem, "1");
			if (insertDiem) {
			    output.writeBoolean(true);
			} else {
			    output.writeBoolean(false);
			}
			break;
		    }

		}

	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
