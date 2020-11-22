package thuchanh4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static thuchanh4.Connect.*;

public class DAO {

    public static void insertAllStudent(List<Student> students) {
        Connection connection = getConnection();
        students.forEach(e -> {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO BANGDIEM (HoVaTen, MaSV, IDLop, DBT1, DBT2, DBT3, DGK) VALUES (?,?,?,?,?,?,?);");){
                statement.setString(1, e.getHoTen());
                statement.setString(2, e.getMaSV());
                statement.setString(3, e.getIdLop());
                statement.setFloat(4, e.getDbt1());
                statement.setFloat(5, e.getDbt2());
                statement.setFloat(6, e.getDbt3());
                float dgk = (e.getDbt1() + e.getDbt2() + e.getDbt3()) / 3;
                statement.setFloat(7, dgk);
                int executeUpdate = statement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public static String getAllDGK() {
        StringBuilder bd = new StringBuilder();
        try (PreparedStatement statement = getConnection().prepareStatement("SELECT HoVaTen, DGK FROM BANGDIEM;");){
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String format = String.format("%-20s | %-10.2f\n", resultSet.getString("HoVaTen"), resultSet.getFloat("DGK"));
                    bd.append(format);
                }
            }
            return bd.toString();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }
}
