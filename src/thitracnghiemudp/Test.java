package thitracnghiemudp;

import java.io.Console;

public class Test {

    public static void main(String[] args) {
	Console con = System.console();

	// Checking If there is no console available, then exit.
	if (con == null) {
	    System.out.print("No console available");
	    return;
	}
//          
	// to read password and then display it
	System.out.println("Enter the password: ");
	char[] ch = con.readPassword();
	// Password save char type

	// converting char array into string
	String pass = String.valueOf(ch);
	System.out.println("Password is: " + pass);
    }
}
