package thithu;

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
			instance = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=LTM",
				"minhduy", "minhduy");
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
	    PreparedStatement pre = connection.prepareStatement(
		    "SELECT UserName, PassWord FROM LTM_TAIKHOAN WHERE UserName = ? AND PassWord = ?;");

	    pre.setString(1, username);
	    pre.setString(2, password);
	    ResultSet rs = pre.executeQuery();
	    return rs.next();
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return false;
    }

    public static ResultSet diemTrungBinh() {
	Connection connection = getConnection();
	try {
	    PreparedStatement pre = connection.prepareStatement(
		    "SELECT DiemBT1, DiemBT2, DiemBT3, HoLot, Ten, MaSV, MaLop FROM LTM_DIEM d JOIN LTM_SINHVIEN sv ON d.IDSinhVien = sv.IDSinhVien\r\n"
			    + "JOIN LTM_LOPHOC l ON sv.IDLop = l.IDLop WHERE l.MaLop = 'D15CQAT01-N';");
	    ResultSet rs = pre.executeQuery();
	    return rs;
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return null;
    }

}
