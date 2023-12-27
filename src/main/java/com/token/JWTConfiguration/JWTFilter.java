package com.token.JWTConfiguration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.token.UserConfig.JWTUserDetailsService;

import org.springframework.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter{
	
	JWTHelper jwtHelper;
	JWTUserDetailsService detailsService;
	@Autowired
	public JWTFilter(JWTHelper jwtHelper, @Lazy JWTUserDetailsService detailsService) {
		super();
		this.jwtHelper = jwtHelper;
		this.detailsService = detailsService;
	}



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token  = null;
		String userName = null;
		String TokenHeader = request.getHeader("Authorization");
		
		if(TokenHeader!=null && TokenHeader.startsWith("Bearer")) {
			token  = TokenHeader.substring(7);
		}
		else {
			System.out.println("This is an Invalid Token Header");
		}
		
		if(StringUtils.hasText(token) && jwtHelper.validateToken(token)) {
			userName = jwtHelper.getUsername(token);
			if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				UserDetails userDetails = detailsService.loadUserByUsername(userName);
				UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}else {
				System.out.println("UserName Does not exist in the context Scope");
			}
		}else {
			System.out.println("The Token is Invalid or null");
		}
		
	}

	

}
