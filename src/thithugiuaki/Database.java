package thithugiuaki;

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
			instance = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=kiemtra",
				"kiemtra", "kiemtra");
		    } catch (SQLException e) {
			e.printStackTrace();
		    }
		}
	    }
	}
	return instance;
    }

    public static boolean validate(String username, String password) {
	Connection connection = getConnection();
	try (PreparedStatement pre = connection
		.prepareStatement("SELECT [User], Password FROM [USER] WHERE [User] = ? AND Password = ?;");) {
	    pre.setString(1, username);
	    pre.setString(2, password);
	    try (ResultSet resultSet = pre.executeQuery();) {
		return resultSet.next();
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return false;
    }

    public static int insertUser(String username, String password) {
	Connection connection = getConnection();
	try (PreparedStatement pre_checkExistUser = connection
		.prepareStatement("SELECT [User] FROM [USER] WHERE [User] = ?;");
		PreparedStatement pre_insertUser = connection
			.prepareStatement("INSERT INTO [USER]([User], Password) VALUES (?,?);");) {
	    pre_checkExistUser.setString(1, username);
	    ResultSet checkExistUser = pre_checkExistUser.executeQuery();
	    if (!checkExistUser.next()) {

		pre_insertUser.setString(1, username);
		pre_insertUser.setString(2, password);
		int i = pre_insertUser.executeUpdate();
		if (i != 0) {
		    return 0; // thanh cong
		} else {
		    return -1; // that bai
		}
	    } else {
		return -2; // ton tai
	    }

	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return -1;
    }

}
