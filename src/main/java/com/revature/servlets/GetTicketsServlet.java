package com.revature.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Tickets;
import com.revature.models.Users;
import com.revature.service.TicketService;


public class GetTicketsServlet extends HttpServlet {

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
		System.out.println("Getting Ticket test");

		ObjectMapper om = new ObjectMapper();
		Users user = om.readValue(request.getReader(), Users.class);
		
		ArrayList<Tickets> tickets = new ArrayList<Tickets>();
		tickets = TicketService.getTicketFromUseridSevice(user);
		
		response.setStatus(201); 
		om.writeValue(response.getWriter(), tickets);

		System.out.println("Ticket based on User ID Retrieved");
	}

}
