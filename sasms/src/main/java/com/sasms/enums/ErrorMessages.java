package com.sasms.enums;

import lombok.Getter;
import lombok.Setter;

public enum ErrorMessages {

	MISSING_REQUIRED_FIELD("---> Missing required field. Please check documentation for required fields"),
	RECORD_ALREADY_EXISTS("---> Record already exist"),
	INTERNAL_SERVER_ERROR("---> Internal Server Error"),
	NO_RECORD_FOUND("---> Record with provided Id is not found"),
	AUTHENTICATION_FAILED("---> Authentication failed"),
	COULD_NOT_UPDATE_RECORD("---> Could not update record"),
	COULD_NOT_DELETE_RECORD("---> Could not delete record"),
	EMAIL_ADDRESS_NOT_VARIFIED("---> Email Address could not be varified");
	
	
	@Getter
	@Setter
	private String errorMessage;
	
	ErrorMessages(String description) {
		errorMessage = description;
	}
	
}
