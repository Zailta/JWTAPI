package com.token.Repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.token.Bean.User;


public class UserMockRepository {

	private static final Map<String, User> users = new HashMap<String, User>();
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	public void InMemoryUserRepository() {
		
		User user = new User();
		user.setUserId("JWT-675898-IMP");
		user.setUserName("johnDoe");
		user.setFirstName("John");
		user.setLastName("Doe");
		user.setRole("ROLE_USER");
		user.setPassowrd(bCryptPasswordEncoder.encode("Random#@1"));
		users.put("johnDoe", user);
	}
	
	
	public User findUserByUserName(String userName) {
		return users.get("johnDoe");
	}
}
