package com.sasms.ui.model.responce;

import java.io.Serializable;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseModel  implements Serializable{

	@Setter(value = AccessLevel.NONE)
	@Getter(value = AccessLevel.NONE)
	private static final long serialVersionUID = -7385956996095850514L;
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	
	private List<AddressResponseModel> addresses;


}
