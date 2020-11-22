package phongchat;

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
			instance = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ChatApp",
				"sa", "Aa123456!");
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
		.prepareStatement("SELECT Username, Password FROM Users WHERE Username = ? AND Password = ?;");) {
	    pre.setString(1, username);
	    pre.setString(2, password);
	    ResultSet rs = pre.executeQuery();
	    return rs.next();

	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return false;
    }

    public static boolean isMaPhong(int maPhong) {
	try {
	    Connection connection = getConnection();
	    PreparedStatement pre = connection
		    .prepareStatement("SELECT IdPhongChat FROM PhongChat WHERE IdPhongChat = ?;");
	    pre.setInt(1, maPhong);
	    ResultSet rs = pre.executeQuery();
	    return rs.next();

	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return false;
    }

    public static String getNoiDungChat(int maPhong) {
	Connection connection = getConnection();
	try {
	    PreparedStatement pre = connection.prepareStatement(
		    "SELECT u.TenUser,ndc.NoiDung FROM Users u JOIN NoiDungChat ndc ON u.IdUser = ndc.IdUser "
			    + "JOIN PhongChat p ON ndc.IdPhongChat = p.IdPhongChat WHERE p.IdPhongChat = ?;");
	    pre.setInt(1, maPhong);
	    ResultSet rs = pre.executeQuery();
	    StringBuilder builder = new StringBuilder();
	    while (rs.next()) {
		builder.append(rs.getString(1)).append(": ").append(rs.getString(2)).append("\n");
	    }
	    return builder.toString();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return null;
    }

}
