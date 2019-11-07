package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Users;
import com.revature.util.ConnectionUtil;

public class UserDao {

	public static Users logIfExist(String username, String password) {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM ers_users WHERE ers_username = ? AND ers_password = ?";
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, username);
			statement.setString(2, password);

			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			Users user = extractUserInfo(resultSet);
			return user;
		} catch (SQLException e) {
			//e.printStackTrace();
			return null;
		}
	}

	public static Users extractUserInfo(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("ers_users_id");
		String username = resultSet.getString("ers_username");
		String password = String.valueOf(resultSet.getString("ers_password").hashCode());
		// attempt salt
		// String password =
		// LoginServlet.passwordHashNSalt(resultSet.getString("ers_password")).toString();
		String firstname = resultSet.getString("user_first_name");
		String lastname = resultSet.getString("user_last_name");
		String email = resultSet.getString("user_email");
		int role_id = resultSet.getInt("user_role_id");
		// attempt to salt again later
		Users user = new Users(id, username, password, firstname, lastname, email, role_id);
		return user;
	}

}
