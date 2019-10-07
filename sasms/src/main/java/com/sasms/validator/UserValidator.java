package com.sasms.validator;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sasms.service.UserService;
import com.sasms.shared.dto.UserDetailDto;

@Component
public class UserValidator implements Validator {

	
	@Autowired
	private UserService userService;
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		UserDetailDto user = (UserDetailDto) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Not Empty");
		
		if(!user.getEmail().contains("@")) {
			errors.rejectValue("email", "Invalid.email.address");
		}
		
		if(userService.getUserByEmail(user.getEmail())!=null) {
			errors.rejectValue("email", "Dublicate.userForm.email");
		}
		
		 ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

//			Email Varification check
//        if (!user.getEmailVarificationToken().equals(user.getPassword())) {
//            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
//        }

	}

}
