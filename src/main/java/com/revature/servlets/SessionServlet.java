package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Credentials;

/**
 * Example of using Session Scope
 *
 */
public class SessionServlet extends HttpServlet {
	public void service(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {

		// Add CORS headers
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "content-type");
		super.service(request, response);
		
	}
	
	
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) 
			throws IOException, ServletException {
		// Deserializing JSON data
		ObjectMapper om = new ObjectMapper();
		Credentials credentials = om.readValue(request.getReader(), Credentials.class);
		
		// Get or create session
		// Supplies a JSESSIONID cookie
		HttpSession session = request.getSession();
		
		Cookie cookie = new Cookie("my-cookie", "some-value");
		response.addCookie(cookie);
		
		// set username on session
		session.setAttribute("username", credentials.getUsername());
		

		
		response.setStatus(200);		
	}
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
			throws IOException, ServletException {
		// Retrieve session if it exists
		HttpSession session = request.getSession(false);
		
		// Separating flow based on whether they have session
		if (session != null) {
			response.getWriter().write("Welcome back " + session.getAttribute("username"));
		} else {
			response.getWriter().write("Please login");
		}
	}
}
