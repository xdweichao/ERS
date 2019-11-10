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
import com.revature.daos.TicketDao;
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

	// get ticket from all user but check if roleid = 2
	// create ticket
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Getting Ticket test");
		int userRoleID = Integer.parseInt(request.getParameter("role"));
		//int userID = Integer.parseInt(request.getParameter("userid"));

		// Get the userid from session
		/*Cookie userRoleIDFromCookie[] = request.getCookies();
		int userRoleID = 1;
		for (Cookie c : userRoleIDFromCookie) {
			if (c.getName().equals("UserRoleIDCookie")) {
				userRoleID = Integer.parseInt(c.getValue());
			}
		}*/

		System.out.println("Role value is " + userRoleID);

		// get id from user, not from session
		ObjectMapper om = new ObjectMapper();

		// Users user = om.readValue(request.getReader(), Users.class);
		ArrayList<Tickets> tickets = new ArrayList<Tickets>();

		tickets = TicketService.getTicketFromAllUsersSevice();
		System.out.println(tickets);
		response.setStatus(201);
		om.writeValue(response.getWriter(), tickets);
		System.out.println("Ticket based on User ID Retrieved");
	}

	// update ticket from user

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Update Ticket test");
		// Get the user and role from session
		//Cookie userRoleIDFromCookie[] = request.getCookies();
		int userRoleID = Integer.parseInt(request.getParameter("role"));
		int userID = Integer.parseInt(request.getParameter("userid"));
		//for (Cookie c : userRoleIDFromCookie) {
			//if (c.getName().equals("UserRoleIDCookie")) {
				//userRoleID = Integer.parseInt(c.getValue());
			//}
			//if (c.getName().equals("UserIDCookie")) {
				//userID = Integer.parseInt(c.getValue());
			//}
		//}

		System.out.println("Role value is " + userRoleID);
		System.out.println("User ID value is " + userID);
		ObjectMapper om = new ObjectMapper();
		Tickets updateTicketInfo = om.readValue(request.getReader(), Tickets.class);

		if (userRoleID == 2) {
			// display user and role id
			System.out.println("Is Financial Manager, Beginning Update");

			// get ticket from db and check if auther is resolver
			Tickets ticketInfo = TicketDao.getTicketById(updateTicketInfo.getTicketid());
			System.out.println("Author from Ticket: " + ticketInfo.getAuthorid());

			if (ticketInfo.getAuthorid() != userID) {
				updateTicketInfo.setResolverid(userID);
				updateTicketInfo = tic.updateTicket(updateTicketInfo);

				response.setStatus(201);

				om.writeValue(response.getWriter(), updateTicketInfo);
				System.out.println("Update Complete");
			} else {
				response.setStatus(403);
				System.out.println("You can't approve/deny your own ticket!");

			}
		} else {

			response.setStatus(403);
			System.out.println("You're not an financial manager");
			System.out.println("Update Failed to to Unauthorization");

			om.writeValue(response.getWriter(), "Access Denied");
		}
		// ArrayList<Tickets> tickets = new ArrayList<Tickets>();
		// System.out.println(updateTicketInfo.getAuthorid());


	}

}
