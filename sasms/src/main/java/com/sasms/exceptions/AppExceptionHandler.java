package com.sasms.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.sasms.ui.model.responce.ErrorMessage;


// Controllers that belong to those base packages or sub-packages 
// thereof will be included, e.g.: @ControllerAdvice(basePackages="org.my.pkg") or 
// @ControllerAdvice(basePackages={"org.my.pkg", "org.my.other.pkg"})}. 

@ControllerAdvice(value = "com.sasms.ui.controller.rest;")
public class AppExceptionHandler {

	
//	@ExceptionHandler(value = {UserServiceException.class, NullPointerException.class})
	@ExceptionHandler(value = UserServiceException.class)
	public ResponseEntity<Object> handleUserServiceException(UserServiceException ex, WebRequest request){
	
		return new ResponseEntity<>(new ErrorMessage(new Date(), ex.getMessage()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
