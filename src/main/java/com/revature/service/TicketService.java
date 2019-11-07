package com.revature.service;

import java.util.ArrayList;

import com.revature.daos.TicketDao;
import com.revature.models.Tickets;
import com.revature.models.Users;


public class TicketService {
	TicketDao ticket = new TicketDao();
	
//	public Tickets getTicket(int ticketid) {
//		return TicketDao.getTicket(ticketid);
//	}

//	
//	public Tickets createTickets(Tickets ticket) {
//		ticket = TicketDao.createTicket(ticket);
//		return ticket;
//	}
//	
	public static ArrayList<Tickets> getTicketFromUseridSevice(Users userid){
		ArrayList<Tickets> tickets = new ArrayList<Tickets>();
		tickets = TicketDao.getAllTicketsFromUser(userid);
		return tickets;
	}
//	
//	public ArrayList<Tickets> getReviewTickets(Users username){
//		ArrayList<Tickets> tickets = new ArrayList<Tickets>();
//		tickets = TicketDao.getReviewTickets(username);
//		return tickets;
//	}
//	
//	public void closeTicket(Tickets ticketInfo) {
//		TicketDao.closeTicket(ticketInfo);
//	}
}
