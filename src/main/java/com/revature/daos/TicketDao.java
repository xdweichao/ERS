package com.revature.daos;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.revature.models.Tickets;
import com.revature.models.Users;
import com.revature.util.ConnectionUtil;


public class TicketDao {

	private static Tickets extractTicket(ResultSet resultSet) throws SQLException {	
		int id = resultSet.getInt("reimb_id");
		double amount = resultSet.getDouble("reimb_amount");
		Timestamp datesubmitted = resultSet.getTimestamp("reimb_submitted");
		Timestamp dateresolved = resultSet.getTimestamp("reimb_resolved");
		String description = resultSet.getString("reimb_description");
		String receipt = resultSet.getString("reimb_receipt");
		int author = resultSet.getInt("reimb_author");
		int resolver = resultSet.getInt("reimb_resolver");
		int status = resultSet.getInt("reimb_status_id");
		int type = resultSet.getInt("reimb_type_id");
		
		Tickets ticket = new Tickets(id, amount, datesubmitted, dateresolved, description,
				receipt, author, resolver, status, type);
		
		return ticket;
	}
	


	public static ArrayList<Tickets> getAllTicketsFromUser(Users userid) {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM ers_reimbursement WHERE reimb_author = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, userid.getUserid());

			ResultSet resultSet = statement.executeQuery();
			
			ArrayList<Tickets> tickets = getTicketList(resultSet);
			return tickets;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
}
	
	private static ArrayList<Tickets> getTicketList(ResultSet resultset){
		try {
			ArrayList<Tickets> tickets = new ArrayList<Tickets>();
			while(resultset.next()) {
				tickets.add(extractTicket(resultset));
			}
			return tickets;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
