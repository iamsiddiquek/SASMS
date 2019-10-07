package com.sasms.security.Impl;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sasms.service.UserService;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	
	private final UserService userService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public WebSecurity(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()						// configure web-services end point of our application as public and others as protected.
		.antMatchers(HttpMethod.POST, SecurityConstraints.SIGN_UP_URL)	// any HTTP Post request sent to the */users*  should be authorized
		.permitAll()													// should be authorized or permit all requests in this url.
		.anyRequest().authenticated()									// any other web-service request should be authenticated.
		.and().addFilter(getAuthenticationFilter())						// and addFilter will check for AuthenticationFilter constructor for login credentials.
		.addFilter(new AuthorizationFilter(authenticationManager()))	// and addFilter will check for AuthorizationFilter with login Credentials if he is valid or not.

		
		// #####  Problem  ######
		// if we are having pages and and session management application
		// this will cache the sessions of the users.
		// if we are having 5 users than app will create and save the cache 
		// of the users session. with authorization.
		// and somehow we don't wanna to give authorization then 

		// #### Solution ####
		// our app will take authorization from cache and authorized the user
		// so to resolve this issue we have to make this STATELESS.
		
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		
		
	}

	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
	}

	
	public AuthenticationFilter getAuthenticationFilter() throws Exception {
		final AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager());
		authenticationFilter.setFilterProcessesUrl("/users/login");
		return authenticationFilter;
	}


	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}
}