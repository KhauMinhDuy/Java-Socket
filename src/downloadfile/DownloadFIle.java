package downloadfile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadFIle {

    public static void main(String[] args) throws IOException {
	String link = "https://kynguyenlamdep.com/wp-content/uploads/2020/01/hinh-anh-dep-hoa-bo-cong-anh-700x466.jpg";
	File out = new File("D:\\Development\\Java\\Code\\JavaNetwork\\files\\anh12.jpg");

	URL url = new URL(link);
	HttpURLConnection con = (HttpURLConnection) url.openConnection();
	long fileSize = con.getContentLengthLong();

	BufferedInputStream br = new BufferedInputStream(con.getInputStream());
	BufferedOutputStream fileout = new BufferedOutputStream(new FileOutputStream(out), 1024);

	byte[] buffer = new byte[1024];
	double downloaded = 0.0d;
	double percen = 0.0d;
	int read = -1;
	while ((read = br.read()) != -1) {
	    fileout.write(buffer, 0, read);
	    downloaded += read;
	    percen = (downloaded * 100) / fileSize;
	    String format = String.format("%.4f", percen);
	    System.out.println(" Download " + format + "%");
	}
	System.out.println("Download Success");

    }
}
