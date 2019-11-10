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
import com.revature.models.TicketCreator;
import com.revature.models.Tickets;
import com.revature.models.Users;
import com.revature.service.TicketService;

public class EmpActionServlet extends HttpServlet {

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
	
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// CORS Headers, mentioned in class but unsure, when this will be used yet in
		// our project
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Credentials", "true");
		resp.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
		resp.setHeader("Access-Control-Allow-Headers",
				"Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");

		super.service(req, resp);
	}

	// get ticket from userid (from cookie)
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Getting Ticket test");

		// Get the userid from session
	//	Cookie userIDFromCookie[] = request.getCookies();
	//	Users userSubmittedInformation = om.readValue(request.getReader(), Users.class);
		int userID = Integer.parseInt(request.getParameter("userid"));
	//	for (Cookie c : userIDFromCookie) {
		//	if (c.getName().equals("UserIDCookie")) {
			//	userID = Integer.parseInt(c.getValue());
		//	}
	//	}

		System.out.println("Cookie value is Userid " + userID);

		// get id from user, not from session
		ObjectMapper om = new ObjectMapper();

		// Users user = om.readValue(request.getReader(), Users.class);
		ArrayList<TicketCreator> tickets = new ArrayList<TicketCreator>();

		tickets = TicketService.getTicketFromUseridSevice(userID);
		System.out.println(tickets);
		response.setStatus(201);
		om.writeValue(response.getWriter(), tickets);

		System.out.println("Ticket based on User ID Retrieved");
	}

	// create ticket with userid from cookie
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Create Ticket test");
		
		/*Cookie userIDFromCookie[] = request.getCookies();
		int userID = -1;
		for (Cookie c : userIDFromCookie) {
			if (c.getName().equals("UserIDCookie")) {
				userID = Integer.parseInt(c.getValue());
			}
		}*/
		ObjectMapper om = new ObjectMapper();
		Tickets createTicketInfo = om.readValue(request.getReader(), Tickets.class);
		int userID = createTicketInfo.getAuthorid();
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
