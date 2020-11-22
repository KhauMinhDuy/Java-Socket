package phongchat;

import static phongchat.DAO.getNoiDungChat;
import static phongchat.DAO.isMaPhong;
import static phongchat.DAO.validate;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int SERVER_PORT = 61298;

    public static void main(String[] args) {
	Server.start();
    }

    private static void start() {
	try (ServerSocket server = new ServerSocket(SERVER_PORT);) {
	    System.out.println(">>>> SERVER RUNNING <<<< ");
	    try (Socket socket = server.accept();
		    DataOutputStream output = new DataOutputStream(socket.getOutputStream());
		    DataInputStream input = new DataInputStream(socket.getInputStream());) {
		System.out.println(">>>> CLIENT CONNECT <<<<");

		do {
		    String username = input.readUTF();
		    String password = input.readUTF();
		    boolean validate = validate(username, password);
		    output.writeBoolean(validate);
		    if (validate) {
			break;
		    }
		} while (true);

		int choose;
		do {
		    choose = input.readInt();
		    switch (choose) {
		    case 1:
			try {
			    int maPhong = Integer.parseInt(input.readUTF());
			    if (isMaPhong(maPhong)) {
				output.writeUTF("Tim Thay Phong");
				String noiDungChat = getNoiDungChat(maPhong);
				output.writeUTF(noiDungChat);
			    } else {
				output.writeUTF("Khong Tim Thay Phong");
				output.writeUTF("");
			    }
			} catch (Exception e) {
			    output.writeUTF("Ma Phong sai");
			    output.writeUTF("");
			    e.printStackTrace();
			}
			break;
		    case 2:
			break;
		    }
		} while (choose != 0);
	    }
	} catch (

	IOException e) {
	    e.printStackTrace();
	}
    }
}
