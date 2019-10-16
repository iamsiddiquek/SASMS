package com.sasms.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sasms.shared.dto.UserDetailDto;

public interface UserService extends UserDetailsService {

	// Spring Web Application.
	void save(UserDetailDto userDetailDto);
	
	// create user for web-service based 
	UserDetailDto createUser(UserDetailDto userDetailDto);

	UserDetailDto getUserByEmail(String email);

	UserDetailDto getUserByUserId(String id);

	UserDetailDto updateUser(String id, UserDetailDto userDto);

	boolean checkUserByEmail(String email);

	void deleteUser(String id);

	List<UserDetailDto> getUsers(int page, int limit);

	
	
}
