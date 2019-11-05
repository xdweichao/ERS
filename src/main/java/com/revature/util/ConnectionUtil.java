package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

	public static Connection getConnection() {
		String url = "jdbc:postgresql://ersdatabase.cb4kahoa7axw.us-east-2.rds.amazonaws.com:5432/postgres";
		//String url = "jdbc:postgresql://localhost:5432/postgres";
		try {
			return DriverManager.getConnection(url, "postgres",  "revature");
				//System.getenv("Wei_Username"),System.getenv("Wei_Password"));
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Unable to connect to database.");
			return null;
		}
	}
}
