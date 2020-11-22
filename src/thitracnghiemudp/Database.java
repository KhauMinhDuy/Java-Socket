package thitracnghiemudp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

    private static Connection instance = null;

    private Database() {
    }

    public static Connection getConnection() {
	if (instance == null) {
	    synchronized (Database.class) {
		if (instance == null) {
		    try {
			instance = DriverManager.getConnection(
				"jdbc:sqlserver://localhost:1433;databaseName=KIEMTRALTM", "minhduy", "minhduy");
		    } catch (SQLException e) {
			e.printStackTrace();
		    }
		}
	    }
	}
	return instance;
    }

    public static boolean validate(String username, String password) {
	try {
	    Connection connection = getConnection();
	    PreparedStatement pre = connection
		    .prepareStatement("SELECT UserName, PassWord FROM SINHVIEN WHERE UserName = ? AND PassWord = ?;");

	    pre.setString(1, username);
	    pre.setString(2, password);
	    ResultSet resultSet = pre.executeQuery();
	    return resultSet.next();

	} catch (SQLException e) {
	    e.printStackTrace();
	    return false;
	}
    }

//    public static void main(String[] args) {
//	Connection connection = getConnection();
//	try {
//	    if (connection.isValid(1)) {
//		System.out.println("Connect");
//	    } else {
//		System.out.println("Fail");
//	    }
//	} catch (SQLException e) {
//	    e.printStackTrace();
//	}
//    }
}
