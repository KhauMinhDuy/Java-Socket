package exemples;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GetIP {

    public static void main(String[] args) {
//	InetAddress address = null;
//	try {
//	    address = InetAddress.getByName("www.javatutorial.com");
//
//	} catch (UnknownHostException e) {
//	    System.exit(2);
//	    e.printStackTrace();
//	}
//
//	System.out.println(address.getHostName() + " = " + address.getHostAddress());

	InetAddress address = null;
	String hostName = null;

	try {
	    address = InetAddress.getLocalHost();
	    hostName = address.getHostName();

	    System.out.println("Your IP Address: " + address);
	    System.out.println("Your HostName: " + hostName);
	} catch (UnknownHostException e) {
	    e.printStackTrace();
	}
    }

}
