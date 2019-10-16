package com.sasms.ui.model.request;

import java.util.List;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequestModel {

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	
	private List<AddressRequestModel> addresses;
	
	
}
