package com.sasms.shared.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserDetailDto implements Serializable{

	@Setter(value = AccessLevel.NONE)
	@Getter(value = AccessLevel.NONE)
	private static final long serialVersionUID = 2695940262429582663L;

	private Long   id;
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String encryptedPassword;
	private String emailVarificationToken;
	private Boolean emailVarificationStatus=false;
	
	private List<AddressDto> addresses;

}
