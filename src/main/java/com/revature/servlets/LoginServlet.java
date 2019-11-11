package com.revature.servlets;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.daos.UserDao;
import com.revature.models.Users;
import com.revature.service.LoginService;
import org.apache.commons.codec.binary.Hex;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
		resp.setHeader("Access-Control-Allow-Credentials", "true");
		resp.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
		resp.setHeader("Access-Control-Allow-Headers",
				"Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");

		super.service(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// read submitted JSON object and check if exist, attempt login
		ObjectMapper om = new ObjectMapper();
		Users userSubmittedInformation = om.readValue(req.getReader(), Users.class);

		String username = userSubmittedInformation.getUsername();
		String password = userSubmittedInformation.getPassword();
		String salt = "1234";
		int iterations = 100;
		int keyLength = 512;
		char[] passwordChars = password.toCharArray();
		byte[] saltBytes = salt.getBytes();
		
		byte[] hashedBytes = hashPassword(passwordChars, saltBytes, iterations, keyLength);
		String hashedPassword = Hex.encodeHexString(hashedBytes);
		
		//check inputed password after hashing
		//System.out.println(hashedPassword);
		// check if user exist
		if (LoginService.authenticate(username, hashedPassword)) {

			// get the old session and invalidate
			HttpSession oldSession = req.getSession(false);
			if (oldSession != null) {
				oldSession.invalidate();
			}

			// generate a new session
			HttpSession newSession = req.getSession(true);

			// setting session lifespan to 5 mins
			newSession.setMaxInactiveInterval(5 * 60);

			Users userInfo = UserDao.logIfExist(username, hashedPassword);

			// adds userID cookie
			Cookie UserIDCookie = new Cookie("UserIDCookie", String.valueOf(userInfo.getUserid()));
			resp.addCookie(UserIDCookie);

			// adds User's Role ID
			Cookie UserRoleIDCookie = new Cookie("UserRoleIDCookie", String.valueOf(userInfo.getRole()));
			resp.addCookie(UserRoleIDCookie);

			resp.setStatus(201);

			om.writeValue(resp.getWriter(), userInfo);
			// check cookie
			// om.writeValue(resp.getWriter(), UserCookie);

		} else {
			System.out.println("Invalid Login");
			resp.setStatus(403);
			resp.getWriter().write("403");
		}
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}


	    public static byte[] hashPassword( final char[] password, final byte[] salt, final int iterations, final int keyLength ) {

	        try {
	            SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
	            PBEKeySpec spec = new PBEKeySpec( password, salt, iterations, keyLength );
	            SecretKey key = skf.generateSecret( spec );
	            byte[] res = key.getEncoded( );
	            return res;
	        } catch ( NoSuchAlgorithmException | InvalidKeySpecException e ) {
	            throw new RuntimeException( e );
	        }
	    }
}
