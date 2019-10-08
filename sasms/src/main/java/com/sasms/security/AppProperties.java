package com.sasms.security;

import org.springframework.stereotype.Component;

@Component
public interface AppProperties {
	String getTokenSecret();
	
}
