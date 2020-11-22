package bai1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO {

    private static Connection instance = null;

    private DAO() {
    }

    public static Connection getConnection() {
	if (instance == null) {
	    synchronized (DAO.class) {
		if (instance == null) {
		    try {
			instance = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Bai1",
				"bai1", "bai1");
		    } catch (SQLException e) {
			e.printStackTrace();
		    }
		}
	    }
	}
	return instance;
    }

    public static int validate(String username, String password) {
	Connection connection = getConnection();
	try (PreparedStatement pre = connection.prepareStatement("SELECT dbo.FN_Login(?, ?)");) {
	    pre.setString(1, username);
	    pre.setString(2, password);
	    ResultSet resultSet = pre.executeQuery();
	    resultSet.next();
	    return resultSet.getInt(1);
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return 0;
    }

//    public static void main(String[] args) {
//	Connection connection = getConnection();
//	try {
//	    if (connection.isValid(1)) {
//		System.out.println("Connect");
//	    }
//	} catch (SQLException e) {
//	    e.printStackTrace();
//	}
//    }
}
