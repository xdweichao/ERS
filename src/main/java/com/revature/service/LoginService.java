package com.revature.service;

import com.revature.daos.UserDao;
import com.revature.models.Users;

public class LoginService {
	public static boolean authenticate(String username, String passwordToHash) {
		if (username==null) {
			return false;
		}
		
		Users userInfo = UserDao.logIfExist(username, passwordToHash);
		String protectedPassword = String.valueOf(passwordToHash.hashCode());

		if ((userInfo != null) && (protectedPassword.equals(userInfo.getPassword()))) {
			System.out.println("User Exist and Correct Password");
			return true;
		} 
		
		return false;
		
	}
}
