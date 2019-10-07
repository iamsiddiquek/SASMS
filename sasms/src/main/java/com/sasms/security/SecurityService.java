package com.sasms.security;

public interface SecurityService {

	//current logged In user.
	String findLoggedInUsername();
	
	//Auto Login after Registration...
	void autoLogin(String email, String password);
	
	
}
