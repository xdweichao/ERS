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
	
	protected static List<Users> getAllUsers() {
		try (Connection connection = ConnectionUtil.getConnection()) {
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM ers_users";
			ResultSet resultSet = statement.executeQuery(query);

			List<Users> UserList = new ArrayList<>();

			while (resultSet.next()) {
				Users User = extractUserInfo(resultSet);
				UserList.add(User);
			}
			return UserList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	public static boolean checkIfUserExist(String username) {
		System.out.println(username + " checking if exisiting...");

		try (Connection connection = ConnectionUtil.getConnection()) {
			String query = "SELECT * FROM ers_users WHERE ers_username = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, username);

			// Check if user exist
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				Users user = extractUserInfo(resultSet);
				System.out.println("yes " + user.getUsername() + " exist");

				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("unable to fetch data from database");
		}
		return false;
	}

	protected static Users extractUserInfo(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("ers_users_id");
		String username = resultSet.getString("ers_username");
		String password = resultSet.getString("ers_password");
		String firstname = resultSet.getString("user_first_name");
		String lastname = resultSet.getString("user_last_name");
		String email = resultSet.getString("user_email");
		int role_id = resultSet.getInt("user_role_id");

		Users user = new Users(id, username, password, firstname, lastname, email, role_id);
		return user;
	}

}
