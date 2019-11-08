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


public class CreateTicketsServlet extends HttpServlet {

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

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Create Ticket test");
		Cookie userIDFromCookie[] = request.getCookies();
		int userID = -1;
		for (Cookie c : userIDFromCookie) {
			if (c.getName().equals("UserIDCookie")) {
				userID = Integer.parseInt(c.getValue());
			}
		}
		ObjectMapper om = new ObjectMapper();
		Tickets createTicketInfo = om.readValue(request.getReader(), Tickets.class);
		
		System.out.println(createTicketInfo);
		System.out.println(userID);
		
		createTicketInfo.setAuthorid(userID);
		createTicketInfo = tic.createTickets(createTicketInfo);
		
		//ArrayList<Tickets> tickets = new ArrayList<Tickets>();
		//System.out.println(createTicketInfo.getAmount());
		
		
		
		response.setStatus(201); 
		om.writeValue(response.getWriter(), createTicketInfo);


		System.out.println("Creation Complete");
	}

}
