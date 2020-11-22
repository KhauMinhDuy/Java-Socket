package thitracnghiem;

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

    public static String getMaSV(String username) {
	Connection connection = Database.getConnection();
	try {
	    PreparedStatement pre = connection.prepareStatement("SELECT MASV FROM SINHVIEN WHERE UserName = ?;");
	    pre.setString(1, username);
	    ResultSet rs = pre.executeQuery();
	    if (rs.next()) {
		return rs.getString("MASV");
	    }

	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return null;
    }

    public static boolean checkLogin(String username, String password) {
	Connection connection = Database.getConnection();
	try {
	    PreparedStatement pre = connection
		    .prepareStatement("SELECT UserName, PassWord FROM SINHVIEN WHERE UserName = ? AND PassWord = ?;");
	    pre.setString(1, username);
	    pre.setString(2, password);
	    ResultSet result = pre.executeQuery();
	    if (result.next()) {
		return true;
	    }

	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return false;
    }

    public static ResultSet getAllBoDe() {
	Connection connection = Database.getConnection();
	try {
	    PreparedStatement pre = connection.prepareStatement("SELECT * FROM BODE;", ResultSet.TYPE_SCROLL_SENSITIVE,
		    ResultSet.CONCUR_UPDATABLE);
	    ResultSet resultSet = pre.executeQuery();
	    return resultSet;

	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return null;
    }

    public static int getAllRowBoDe() {
	Connection connection = Database.getConnection();
	try {
	    PreparedStatement pre = connection.prepareStatement("SELECT COUNT(CAUHOI) FROM BODE;");
	    ResultSet rs = pre.executeQuery();
	    if (rs.next()) {
		return rs.getInt(1);
	    }

	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return -1;
    }

    public static boolean insertDiem(String masv, int lan, String ngaythi, int diem, String baithi) {
	Connection connection = Database.getConnection();
	try {
	    PreparedStatement pre = connection.prepareStatement("INSERT INTO BANGDIEM VALUES(?,?,?,?,?);");
	    pre.setString(1, masv);
	    pre.setInt(2, lan);
	    pre.setString(3, ngaythi);
	    pre.setInt(4, diem);
	    pre.setString(5, baithi);
	    int rs = pre.executeUpdate();
	    if (rs != 0) {
		return true;
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return false;
    }

//    public static void main(String[] args) {
//	Connection connection = Database.getConnection();
//	try {
//	    if (connection.isValid(1)) {
//		System.out.println("Connect Success");
//	    }
//	} catch (SQLException e) {
//	    e.printStackTrace();
//	}
//    }

}
