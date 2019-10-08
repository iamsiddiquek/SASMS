package com.sasms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sasms.security.AppProperties;
import com.sasms.security.Impl.AppPropertiesImpl;

@SpringBootApplication
public class SasmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SasmsApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SpringApplicationContext applicationContext() {
		return new SpringApplicationContext();
	}
	
	@Bean("appPropertiesImpl")
	public AppProperties getAppProperties() {
		return new AppPropertiesImpl();
	}
	
}
