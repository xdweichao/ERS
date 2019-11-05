 package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.models.Users;
import com.revature.util.ConnectionUtil;

public class UserDao {
public static boolean checkIfUserExist(String username) {
	System.out.println(username + "check if exisiting...");
	
	try (Connection connection = ConnectionUtil.getConnection()) {
		String query = "SELECT * FROM ers_users WHERE user_name = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, username);

		// Check if user was found
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			Users user = extractUserInfo(resultSet);
			return true;
		}
	} catch (SQLException e) {
		e.printStackTrace();
		System.out.println("unable to select");
	}
	return false;
}

private static Users extractUserInfo(ResultSet resultSet) throws SQLException {
	int id = resultSet.getInt("ers_users_id");
	String username = resultSet.getString("ers_username");
	String password = resultSet.getString("ers_password");
	String email = resultSet.getString("user_email");
	String firstname = resultSet.getString("user_first_name");
	String lastname = resultSet.getString("user_last_name");
	int role_id = resultSet.getInt("user_role_id");

	Users user = new Users(id, username, password, email, firstname, lastname, role_id);
	return user;
}
}
