package com.token.UserConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.token.Bean.User;
import com.token.Repository.UserMockRepository;


public class JWTUserDetailsService implements UserDetailsService{

	@Autowired
	UserMockRepository mockRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		mockRepository.InMemoryUserRepository();
		User findUserByUserName = mockRepository.findUserByUserName(username);
		
		if(findUserByUserName == null) {
			throw new UsernameNotFoundException("User not present in DB");
		}
		 JWTUserDetails jwtUserDetails = new JWTUserDetails(findUserByUserName);
		return jwtUserDetails;
	}


}
