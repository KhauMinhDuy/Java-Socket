import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressDemo {
    public static void main(String[] args) {
	try {
	    InetAddress address = InetAddress.getByName("www.codejava.com");
	    System.out.println(address.getHostAddress());
	    System.out.println(address.getHostName());
	    System.out.println(address.getCanonicalHostName());

	    System.out.println();
	    InetAddress address2 = InetAddress.getByName("8.8.8.8");
	    System.out.println(address2.getHostAddress());
	    System.out.println(address2.getHostName());
	} catch (UnknownHostException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }
}
