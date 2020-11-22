package downloadfile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Demo {

    private static final int BUFFER_SIZE = 4096;

    /**
     * Downloads a file from a URL
     * 
     * @param fileURL HTTP URL of the file to be downloaded
     * @param saveDir path of the directory to save the file
     * @throws IOException
     * @throws IOException
     */
    public static void downloadFile(String fileURL, String saveDir) throws IOException {
	URL url = new URL(fileURL);
	HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
	int responseCode = httpConnection.getResponseCode();

	if (responseCode == HttpsURLConnection.HTTP_OK) {
	    String fileName = "";
	    String disposition = httpConnection.getHeaderField("Content-Disposition");
	    String contentType = httpConnection.getContentType();
	    int contentLength = httpConnection.getContentLength();

	    if (disposition != null) {
		int index = disposition.indexOf("filename=");
		if (index > 0) {
		    fileName = disposition.substring(index + 10, disposition.length() - 1);
		}
	    } else {
		fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1, fileURL.length());
	    }

	    System.out.println("Content-Type: " + contentType);
	    System.out.println("Content-Length: " + contentLength);
	    System.out.println("Content-Disposition: " + disposition);
	    System.out.println("filename: " + fileName);

	    InputStream inputStream = httpConnection.getInputStream();
	    String saveFilePath = saveDir + File.separator + fileName;

	    FileOutputStream outputStream = new FileOutputStream(saveFilePath);

	    int bytesRead = -1;
	    byte[] buff = new byte[BUFFER_SIZE];
	    while ((bytesRead = inputStream.read()) != -1) {
		outputStream.write(buff, 0, bytesRead);
	    }
	    inputStream.close();
	    outputStream.close();
	    System.out.println("File Download Success");
	} else {
	    System.out.println("No fIle download. Server replied http code " + responseCode);
	}
	httpConnection.disconnect();
    }

    public static void main(String[] args) {
	String fileURL = "https://go.microsoft.com/fwlink/?linkid=2137600https://dev.mysql.com/get/Downloads/MySQLInstaller/mysql-installer-web-community-8.0.22.0.msi";
	String saveDir = "D:\\Development\\Java\\Code\\JavaNetwork\\files";

	try {
	    Demo.downloadFile(fileURL, saveDir);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
