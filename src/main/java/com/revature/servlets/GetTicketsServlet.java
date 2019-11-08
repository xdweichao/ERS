package com.revature.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.daos.TicketDao;
import com.revature.models.Tickets;
import com.revature.models.Users;
import com.revature.service.TicketService;


public class GetTicketsServlet extends HttpServlet {

	TicketService tic = new TicketService();
	
	private static final long serialVersionUID = 1L;
	@Override
	public void init() throws ServletException {

		super.init();
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Get Ticket by ID test");
		
		
		ObjectMapper om = new ObjectMapper();
		Tickets getTicketInfo = om.readValue(request.getReader(), Tickets.class);
		int ticketID= getTicketInfo.getTicketid();
//		System.out.println(getTicketInfo);
//		System.out.println(ticketID);
		
		Tickets TicketByIDInfo = TicketDao.getTicketById(ticketID);
		
		//ArrayList<Tickets> tickets = new ArrayList<Tickets>();
		//System.out.println(createTicketInfo.getAmount());
		
		
		
		response.setStatus(201); 
		om.writeValue(response.getWriter(), TicketByIDInfo);


		System.out.println("Get Ticket by ID Complete");
	}

}
