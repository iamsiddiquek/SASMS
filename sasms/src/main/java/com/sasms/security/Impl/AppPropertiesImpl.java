package com.sasms.security.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.sasms.security.AppProperties;

public class AppPropertiesImpl  implements AppProperties {

	@Autowired
	Environment environment;
	
	@Override
	public String getTokenSecret() {
		return environment.getProperty("tokenSecret");
	}

}
