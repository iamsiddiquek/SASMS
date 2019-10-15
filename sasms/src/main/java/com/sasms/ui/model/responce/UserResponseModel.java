package com.sasms.ui.model.responce;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseModel {

	private String userId;
	private String firstName;
	private String lastName;
	private String email;


}