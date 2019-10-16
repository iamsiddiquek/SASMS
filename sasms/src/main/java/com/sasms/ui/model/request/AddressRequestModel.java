package com.sasms.ui.model.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressRequestModel {

	private String city;
	private String country;
	private String streetName;
	private String postalCode;
	private String type;
	
}
