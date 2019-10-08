package com.sasms.security.Impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sasms.SpringApplicationContext;
import com.sasms.io.entity.RoleEntity;
import com.sasms.service.UserService;
import com.sasms.shared.dto.UserDetailDto;
import com.sasms.ui.model.request.UserLoginRequestModel;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

//	Processes an Authentication request.
	private final AuthenticationManager authenticationManager;
	
	@SuppressWarnings("unused")
	@Autowired
	private RoleEntity roleEntity;
	
	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	

	// This method is provided by the *UsernamePasswordAuthenticationFilter* which will run 
	// to authenticate the user credentials provided by *UserLoginRequestModel.class*
	// if the provided credentials it will be given to Authentication Class of Spring 
	// this Authentication class will be utilized in case of *successfulAuthentication(...) method*
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

		UserLoginRequestModel creds = null;
		try {
			creds = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequestModel.class);
		} catch (IOException e) {
//				e.printStackTrace();
			throw new RuntimeException(e);
		}

		return authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));

	}

	@Override
	public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) {
		
		// the username will be takken from REST authentication username/email
		String username = ((User) auth.getPrincipal()).getUsername();
		
		// creating the token with with some basic Constraints
		// this token will be added to the header that every time user 
		// request some resource it should be authenticated before giving resoure to them.
		// localhost:8080/*login* login page is default provided by spring framework
		String token = Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstraints.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SecurityConstraints.getTokenSecret())
				.compact();
		
		// Factory Design pattern
		UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");

		UserDetailDto userDetailDto = userService.getUserByEmail(username);
				
		
		response.addHeader(SecurityConstraints.HEADER_STRING, token);
		response.addHeader("userID", userDetailDto.getUserId());
	}
	
}
