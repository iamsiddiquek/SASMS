package com.sasms;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer { 
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
		.addMapping("/**")  // Exact path mapping URIs (such as "/admin") are supported as well as Ant-style path patterns (such as "/admin/**"). 
		.allowedMethods("*") // Set the HTTP methods to allow, e.g. "GET", "POST", etc.The special value "*" allows all methods. By default "simple" methods, i.e. GET, HEAD, and POST are allowed.
		.allowedOrigins("*"); // The list of allowed origins that be specific origins, e.g. "https://domain1.com", or "*" for all origins/domains. 
		
	}

}
