package com.revature.servlets;

import java.io.IOException;
import java.security.SecureRandom;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.User;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FormServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		System.out.println("Form submission received");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		response.setStatus(201);
		response.getWriter().write("Welcome " + username);
	}
	

}
