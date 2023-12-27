package com.token.UserConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.token.Repository.UserMockRepository;

@Configuration
public class JWTConfiguration {

	@Bean
	public UserMockRepository getUserMockRepository() {
		return new UserMockRepository();
	}
	

}
