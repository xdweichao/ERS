package com.revature.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Tickets;
import com.revature.service.TicketService;

public class FinManActionServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	TicketService tic = new TicketService();

	@Override
	public void init() throws ServletException {

		super.init();
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// get ticket from all user but check if roleid = 2
	// create ticket 
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Getting Ticket test");

		// Get the userid from session
		Cookie userRoleIDFromCookie[] = request.getCookies();
		int userRoleID = 1;
		for (Cookie c : userRoleIDFromCookie) {
			if (c.getName().equals("UserRoleIDCookie")) {
				userRoleID = Integer.parseInt(c.getValue());
			}
		}

		System.out.println("Role value is " + userRoleID);

		// get id from user, not from session
		ObjectMapper om = new ObjectMapper();

		// Users user = om.readValue(request.getReader(), Users.class);
		ArrayList<Tickets> tickets = new ArrayList<Tickets>();

		tickets = TicketService.getTicketFromAllUsersSevice(userRoleID);
		System.out.println(tickets);
		response.setStatus(201);
		om.writeValue(response.getWriter(), tickets);

		System.out.println("Ticket based on User ID Retrieved");
	}

	// update ticket from user

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Update Ticket test");

		ObjectMapper om = new ObjectMapper();
		Tickets updateTicketInfo = om.readValue(request.getReader(), Tickets.class);

		updateTicketInfo = tic.updateTicket(updateTicketInfo);
		
		//ArrayList<Tickets> tickets = new ArrayList<Tickets>();
		//System.out.println(updateTicketInfo.getAuthorid());
		
		
		
		response.setStatus(201); 
		om.writeValue(response.getWriter(), updateTicketInfo);


		System.out.println("Update Complete");
	}

}
