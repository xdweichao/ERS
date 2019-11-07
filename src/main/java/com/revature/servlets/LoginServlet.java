package com.revature.servlets;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

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
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// read submitted JSON object and check if exist, attempt login
		ObjectMapper om = new ObjectMapper();
		Users userSubmittedInformation = om.readValue(req.getReader(), Users.class);

		String username = userSubmittedInformation.getUsername();
		String passwordToHash = userSubmittedInformation.getPassword();

		Users userInfo = UserDao.logIfExist(username, passwordToHash);

		// hash password
		String protectedPassword = String.valueOf(passwordToHash.hashCode());

		// check if user exist
		if ((userInfo != null) && (protectedPassword.equals(userInfo.getPassword()))) {
			System.out.println("User is logged");
			resp.setStatus(201); 
			om.writeValue(resp.getWriter(), userInfo);
		} else {
			System.out.println("Username/Password is incorrect");
		}
	}

	
	// 50% Done Salting logic if we have time
	public final static byte[] saltCode() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[64];
		random.nextBytes(salt);
		return salt;
	}
	// create a consistent salt value
	static byte[] salt = saltCode();
	public static byte[] passwordHashNSalt(String passwordToProtect) {
		try {
			// configure the SHA-512 hash function with our salt
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt);
			// password hashed
			md.update(passwordToProtect.getBytes(Charset.forName("UTF-8")));
			byte[] hashedPassword = md.digest(passwordToProtect.getBytes(StandardCharsets.UTF_8));
			return hashedPassword;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
