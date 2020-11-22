package csdl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Server {
    private static DataOutputStream output;

    public static void main(String[] args) {
	Server.start();
    }

    private static void start() {
	try (ServerSocket server = new ServerSocket(61298);
		Socket socket = server.accept();
		DataInputStream input = new DataInputStream(socket.getInputStream());) {
	    output = new DataOutputStream(socket.getOutputStream());
	    System.out.println(">>>> SERVER RUNNING <<<<");
	    System.out.println(">>>> CLIENT KET NOI <<<<");
	    int choose;
	    do {
		choose = input.readInt();
		switch (choose) {
		case 1: {
		    String ma = input.readUTF();
		    String ten = input.readUTF();
		    String chucvu = input.readUTF();
		    them(ma, ten, chucvu);
		    break;
		}
		case 2: {
		    String ma = input.readUTF();
		    String ten = input.readUTF();
		    String chucvu = input.readUTF();
		    sua(ma, ten, chucvu);
		    break;
		}
		case 3: {
		    String ma = input.readUTF();
		    xoa(ma);
		    break;
		}
		}
	    } while (choose != 0);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private static void xoa(String ma) {
	try {
	    Connection connection = Database.getInstance();
	    PreparedStatement pre = connection.prepareStatement("DELETE QLNV WHERE ma = ?");
	    pre.setString(1, ma);

	    PreparedStatement selectMa = connection.prepareStatement("SELECT ma FROM QLNV WHERE ma = ?;");
	    selectMa.setString(1, ma);
	    ResultSet resultSet = selectMa.executeQuery();

	    if (resultSet.next()) {
		if (pre.executeUpdate() != 0) {
		    output.writeUTF(" \n>>>> Xoa Thanh Cong \n");
		} else {
		    output.writeUTF(" \n>>>> Xoa That Bai \n");
		}
	    } else {
		output.writeUTF(" \n>>>> Khong co Ma nhan vien tuong ung \n");
	    }
	} catch (IOException | SQLException e) {
	    e.printStackTrace();
	}
    }

    private static void sua(String ma, String ten, String chucvu) {
	try {
	    Connection connection = Database.getInstance();
	    PreparedStatement pre = connection.prepareStatement("UPDATE QLNV SET ten = ?, chucvu = ? WHERE ma = ?;");
	    pre.setString(1, ten);
	    pre.setString(2, chucvu);
	    pre.setString(3, ma);

	    PreparedStatement selectMa = connection.prepareStatement("SELECT ma FROM QLNV WHERE ma = ?;");
	    selectMa.setString(1, ma);
	    ResultSet resultSet = selectMa.executeQuery();

	    if (resultSet.next()) {
		if (pre.executeUpdate() != 0) {
		    output.writeUTF(" \n>>>> Sua Thanh Cong \n");
		} else {
		    output.writeUTF(" \n>>>> Sua That Bai \n");
		}
	    } else {
		output.writeUTF(" \n>>>> Khong co Ma nhan vien tuong ung \n");
	    }
	} catch (IOException | SQLException e) {
	    e.printStackTrace();
	}
    }

    private static void them(String ma, String ten, String chucvu) {
	try {
	    Connection connection = Database.getInstance();
	    PreparedStatement pre = connection.prepareStatement("INSERT INTO QLNV(ma, ten, chucvu) VALUES (?,?,?);");
	    pre.setString(1, ma);
	    pre.setString(2, ten);
	    pre.setString(3, chucvu);

	    PreparedStatement selectMa = connection.prepareStatement("SELECT ma FROM QLNV WHERE ma = ?;");
	    selectMa.setString(1, ma);
	    ResultSet resultSet = selectMa.executeQuery();

	    if (resultSet.next()) {
		output.writeUTF(" \n>>>> Ma Trung Lap \n");
	    } else {
		if (pre.executeUpdate() != 0) {
		    output.writeUTF(" \n>>>> Them Thanh Cong \n");
		} else {
		    output.writeUTF(" \n>>>> Them That Bai \n");
		}
	    }
	} catch (IOException | SQLException e) {
	    e.printStackTrace();
	}
    }

}
