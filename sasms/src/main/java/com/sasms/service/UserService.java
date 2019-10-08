package com.sasms.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sasms.shared.dto.UserDetailDto;

public interface UserService extends UserDetailsService {

	// Spring Web Application.
	void save(UserDetailDto userDetailDto);
	
	// create user for web-service based 
	UserDetailDto createUser(UserDetailDto userDetailDto);

	UserDetailDto getUserByEmail(String email);

	UserDetailDto getUserByUserId(String id);

	
	
}
