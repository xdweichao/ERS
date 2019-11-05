package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.daos.UserDao;
import com.revature.models.Users;

public class LoginServlet extends HttpServlet {

	// JDBC on init
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
		resp.setHeader("Access-Control-Allow-Headers", "content-type");
		super.service(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		

		//read submitted JSON object and check if exist, it will return user information [ excluding password ] 
		ObjectMapper om = new ObjectMapper();
		Users userSubmittedInformation = om.readValue(req.getReader(), Users.class);
		om.writeValue(resp.getWriter(), userSubmittedInformation);
		String username = userSubmittedInformation.getUsername();
		System.out.println("Inputted: " + username);
		
		//check if user exist
		boolean userExist = UserDao.checkIfUserExist(username);
		if (UserDao.checkIfUserExist(username)) {
			//code to extract info
		}
	}
}
