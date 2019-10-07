package com.sasms.security.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Service;

import com.sasms.security.SecurityService;

@Service
public class SecurityServiceImpl extends BasicAuthenticationFilter implements SecurityService {

	@Autowired
	AuthenticationManager authenticationManager;

	public SecurityServiceImpl(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}
	
	@Autowired
	UserDetailsService userDetailsService;
	
    private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

	
	@Override
	public String findLoggedInUsername() {
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
		
		if(userDetails instanceof UserDetails) {
			return ((UserDetails)userDetails).getUsername(); // this.equals(getEmail) 
		}
		return null;
	}

	
	@Override
	public void autoLogin(String email, String password) {

		UserDetails userDetails = userDetailsService.loadUserByUsername(email);
		UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
		
		authenticationManager.authenticate(upat);
		
		if(upat.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(upat);
			logger.debug(String.format("Auto login %s successfully!", email));
		}
		
		
		
	}

}
