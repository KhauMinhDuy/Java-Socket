package thuchanh4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

    private static Connection instance = null;

    private Connect() {}

    public static Connection getConnection() {
        if(instance == null) {
            synchronized (Connect.class) {
                if(instance == null) {
                    try {
                        instance = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=thuchanh4", "thuchanh4", "thuchanh4");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }


//    public static void main(String[] args) {
//        try {
//            if(getConnection().isValid(1)) {
//                System.out.println("Connect");
//            } else {
//                System.out.println("Fail");
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
}
