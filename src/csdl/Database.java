package csdl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static Connection instance;

    private Database() {
    }

    public static Connection getInstance() {
	if (instance == null) {
	    synchronized (Database.class) {
		if (instance == null) {
		    try {
			instance = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=QLNV",
				"minhduy", "minhduy");
		    } catch (SQLException e) {
			e.printStackTrace();
		    }
		}
	    }
	}
	return instance;
    }

}
