package com.token.JWTConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.token.UserConfig.JWTUserDetailsService;


@Configuration
@EnableWebSecurity
public class JWTSecurityConfig {
	@Autowired
	JWTEntryPoint entryPoint;
	@Autowired
	@Lazy
	JWTFilter filter;
	
	@Bean
	public UserDetailsService getUserDetailsService() {
		return new JWTUserDetailsService();
	}
	
	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(getUserDetailsService());
		authenticationProvider.setPasswordEncoder(getBCryptPasswordEncoder());
		return authenticationProvider;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector) throws Exception {
		
		MvcRequestMatcher.Builder builder = new MvcRequestMatcher.Builder(introspector);
		httpSecurity.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(authorize->{
			authorize.requestMatchers(builder.pattern("/api/accessToken")).permitAll().anyRequest().authenticated();
		}).exceptionHandling(exception->{
			exception.authenticationEntryPoint(entryPoint);
		}).sessionManagement(session->{
			session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		}).addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		return httpSecurity.build();
		
	}
	
	@Bean
	public AuthenticationManager  getAuthenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

}
