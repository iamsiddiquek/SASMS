package com.sasms.ui.model.responce;

import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressResponseModel extends ResourceSupport {

	private String addressId;
	private String city;
	private String country;
	private String streetName;
	private String postalCode;
	private String type;
	
}
