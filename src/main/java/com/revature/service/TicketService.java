package com.revature.service;

import java.util.ArrayList;

import com.revature.daos.TicketDao;
import com.revature.models.Tickets;
import com.revature.models.Users;


public class TicketService {
	TicketDao ticket = new TicketDao();
	
	public Tickets getTicket(int ticketid) {
		return TicketDao.getTicketById(ticketid);
	}

	
	public Tickets createTickets(Tickets ticket) {
		Double amount = ticket.getAmount();
		String description = ticket.getDescription();
		int author = ticket.getAuthorid();
		int reimbType = ticket.getTypeid();
		ticket = TicketDao.createTicket(amount, description, author, reimbType);
		
		return ticket;
	}

	
	
	
	public Tickets updateTicket(Tickets ticket) {
		int status = ticket.getStatusid();
		int resolver = ticket.getResolverid();
		int ticketId = ticket.getTicketid();

		ticket = TicketDao.updateTicket(ticketId, status, resolver);
		return ticket;
		
	}
	
	public static ArrayList<Tickets> getTicketFromUseridSevice(int userid){
		ArrayList<Tickets> tickets = new ArrayList<Tickets>();
		tickets = TicketDao.getAllTicketsFromUser(userid);
		return tickets;
	}


	public static ArrayList<Tickets> getTicketFromAllUsersSevice(int userRoleID) {
		ArrayList<Tickets> tickets = new ArrayList<Tickets>();
		tickets = TicketDao.getAllTickets();
		return tickets;
	}

	
	
	
	
}
