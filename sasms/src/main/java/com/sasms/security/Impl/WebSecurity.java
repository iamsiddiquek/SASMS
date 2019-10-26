package com.sasms.security.Impl;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
		http
		.cors().and()
		.csrf().disable().authorizeRequests()						// configure web-services end point of our application as public and others as protected.
		.antMatchers(HttpMethod.POST, SecurityConstraints.SIGN_UP_URL)	// any HTTP Post request sent to the */users*  should be authorized
		.permitAll()													// should be authorized or permit all requests in this url.
//		.antMatchers(HttpMethod.GET, "Any URL", "Any Other Url of Get...")
//		.permitAll()
		.antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**")
		.permitAll()
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
	
	@Bean	
	public CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration corsConfig = new CorsConfiguration();
		
		corsConfig
		.setAllowedOrigins(Arrays.asList("http://")); // Set the origins to allow, e.g. {@code "https://domain1.com"}. The special value {@code "*"} allows all domains. By default this is not set.
		corsConfig.setAllowedMethods(Arrays.asList("")); //	Set the HTTP methods to allow, e.g. {"GET", "POST", "PUT"}, etc.The special value {@code "*"} allows all methods.
		corsConfig.setAllowCredentials(true);
		corsConfig.setExposedHeaders(Arrays.asList("Content-Type", "Cache-Control", "Content-Language", "Expires", "Last-Modified", "Authorization"));

		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig); //Register a {@link CorsConfiguration} for the specified path pattern.

		return (CorsConfigurationSource) source;
	}
	
	
}