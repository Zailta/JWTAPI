package com.token.Controller;

import java.util.Date;
import java.util.UUID;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.token.Bean.JWTAutheticationBean;
import com.token.JWTConfiguration.JWTHelper;
import com.token.JWTConfiguration.JWTResponse;


@RestController
@RequestMapping(value = "/api")
public class JWTController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JWTHelper helper;

	@PostMapping(value = "/generateToken")
	public ResponseEntity<JWTResponse> createToken(@RequestBody JWTAutheticationBean user){
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
		String generateToken = helper.generateToken(authentication);
		JWTResponse jwtResponse = new JWTResponse();
		jwtResponse.setToken(generateToken);
		jwtResponse.setDate(new Date());
		jwtResponse.setTracker(UUID.randomUUID().toString());
		return new ResponseEntity<JWTResponse>(jwtResponse, HttpStatus.OK);
		
	}

}
