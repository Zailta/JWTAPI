package com.token.Repository;

import java.util.HashMap;
import java.util.Map;

import com.token.Bean.User;

public class UserMockRepository {

	private final Map<String, User> users = new HashMap<String, User>();
	
	public void InMemoryUserRepository() {
		
		User user = new User();
		user.setUserId("JWT-675898-IMP");
		user.setUserName("johnDoe");
		user.setFirstName("John");
		user.setLastName("Doe");
		user.setRole("ROLE_USER");
		user.setPassowrd("Random#@1");
		users.put("johnDoe", user);
	}
	
	public User findUserByUserName(String userName) {
		return users.get("johnDoe");
	}
}
