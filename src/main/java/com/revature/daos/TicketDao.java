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

		Tickets ticket = new Tickets(id, amount, datesubmitted, dateresolved, description, receipt, author, resolver,
				status, type);

		return ticket;
	}

	public static ArrayList<Tickets> getAllTicketsFromUser(int userid) {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM ers_reimbursement WHERE reimb_author = ?";
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setInt(1, userid);

			ResultSet resultSet = statement.executeQuery();

			ArrayList<Tickets> tickets = getTicketList(resultSet);
			return tickets;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static ArrayList<Tickets> getTicketList(ResultSet resultset) {
		try {
			ArrayList<Tickets> tickets = new ArrayList<Tickets>();
			while (resultset.next()) {
				tickets.add(extractTicket(resultset));
			}
			return tickets;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Tickets getTicketById(int id) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select * from ers_reimbursement natural join ers_reimbursement_status natural join ers_reimbursement_type where reimb_id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {

				return extractTicket(resultSet);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Tickets createTicket(Double amount, String description, int author, int reimbType) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "insert into ers_reimbursement(reimb_amount, reimb_submitted, reimb_description, reimb_author, reimb_status_id, reimb_type_id) \r\n"
					+ "values(?,current_date, ?  , ?, '1', ? ) \r\n" + "RETURNING reimb_id ";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setDouble(1, amount);
			statement.setString(2, description);
			statement.setInt(3, author);
			statement.setInt(4, reimbType);

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				System.out.println("Created TicketID#: " + resultSet.getInt("reimb_id"));
				return getTicketById(resultSet.getInt("reimb_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Tickets updateTicket(int ticketId, int status, int resolver) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "\r\n" + "update ers_reimbursement set reimb_status_id = ?, reimb_resolver = ?, "
					+ "reimb_resolved = current_date where reimb_id = ? returning *";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, status);
			statement.setInt(2, resolver);
			statement.setInt(3, ticketId);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				System.out.println("Updating TicketID: " + resultSet.getInt("reimb_id"));
				return getTicketById(resultSet.getInt("reimb_id"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return null;

	}

	public static ArrayList<Tickets> getAllTickets() {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM ers_reimbursement";
			PreparedStatement statement = connection.prepareStatement(sql);

			ResultSet resultSet = statement.executeQuery();

			ArrayList<Tickets> tickets = getTicketList(resultSet);
			return tickets;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
