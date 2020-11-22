import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashSet;
import java.util.Set;

public class Server {

    private static final int SERVER_PORT = 8888;

    public static void start() {
	try (ServerSocket server = new ServerSocket(SERVER_PORT);
		Socket socket = server.accept();
		DataInputStream input = new DataInputStream(socket.getInputStream());
		DataOutputStream output = new DataOutputStream(socket.getOutputStream());) {

	    System.out.println(">>>> SERVER RUNNING <<<<");
	    System.out.println(">>>> CLIENT KET NOI <<<<");
	    int chonBai;
	    do {
		chonBai = input.readInt();
		switch (chonBai) {
		case 1:
		    int a = input.readInt();
		    int b = input.readInt();
		    int c = input.readInt();
		    int d = input.readInt();
		    double a1 = b / a, b1 = c / a, c1 = d / a;
		    double p = b1 - a1 * a1 / 3;
		    double q = c1 + (2 * a1 * a1 * a1 - 9 * a1 * b1) / 27;
		    double m = q * q / 4 + p * p * p / 27;
		    double n = q / 2 + (double) Math.sqrt(m);
		    double u;
		    if (n >= 0) {
			u = Math.pow(n, 1 / 3);
		    } else {
			u = -1 * Math.pow(-n, 1 / 3);
		    }

		    double x = p / 3 / u - u - a1 / 3;
		    double t1 = 2 / q * Math.sqrt(-m);
		    double t = 1 / 3 * Math.sqrt(t1);
		    double r = q * q / 4 - m;
		    double z = Math.sqrt(r);
		    double u1 = Math.pow(z, 1 / 3);
		    double x1, x2;
		    if (q >= 0) {
			x1 = p / 3 / u1 * Math.cos(t) - u1 * Math.cos(t) - a1 / 3;
			x2 = -p / 3 / u1 * Math.sin(t) - u1 * Math.sin(t);

		    } else {
			x1 = -p / 3 / u1 * Math.cos(t) + u1 * Math.cos(t) - a1 / 3;
			x2 = p / 3 / u1 * Math.sin(t) + u1 * Math.sin(t);
		    }
		    output.writeDouble(m);
		    if (m >= 0) {
			output.writeDouble(x);
		    } else {
			output.writeDouble(x1);
			output.writeDouble(x2);
		    }

		    break;

		case 2:
		    String pathSrc = input.readUTF();
		    if (new File(pathSrc).exists()) {
			output.writeBoolean(true);
		    } else {
			output.writeBoolean(false);
		    }

		    String pathDes = input.readUTF();
		    File fileDes = new File(pathDes);
		    Set<StandardOpenOption> options = new LinkedHashSet<StandardOpenOption>();
		    options.add(StandardOpenOption.CREATE);
		    options.add(StandardOpenOption.READ);
		    options.add(StandardOpenOption.WRITE);
		    FileChannel src = new FileInputStream(new File(pathSrc)).getChannel();
		    FileChannel des = new FileOutputStream(new File(pathDes)).getChannel();
		    long transferTo = src.transferTo(0, src.size(), des);
		    System.out.println(transferTo);
		    if (transferTo == src.size()) {
			output.writeUTF("Success");
		    } else {
			output.writeUTF("Fail");
		    }
		    break;
		}
	    } while (chonBai != 0);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public static void main(String[] args) {
	Server.start();
    }

}
