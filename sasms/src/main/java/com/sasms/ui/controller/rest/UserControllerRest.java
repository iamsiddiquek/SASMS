package com.sasms.ui.controller.rest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sasms.io.repositories.UserRepository;
import com.sasms.service.UserService;
import com.sasms.shared.dto.UserDetailDto;
import com.sasms.ui.model.request.UserDetailRequestModel;
import com.sasms.ui.model.responce.UserDetailResponseModel;

@RestController
@RequestMapping("/users")
public class UserControllerRest {

	
	//################# START USER INJECTED BEANS ##########################
	
	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;

	//################# END USER INJECTED BEANS ##########################
	
	//################# START USER FUNCTIONAL METHODS ##########################	

	// @RequestBody Annotation indicating a method parameter should be bound to the
	// body of the web request.
	// The body of the request is passed through an HttpMessageConverter to resolve
	// the method argument depending on the content type of the request.
	// Optionally, automatic validation can be applied by annotating the argument
	// with @Valid.
	@PostMapping
	public UserDetailResponseModel createUser(@RequestBody UserDetailRequestModel userDetailModel) {

		if (userRepository.findByEmail(userDetailModel.getEmail()) != null)
			throw new RuntimeException("Email is already present into database.");

		UserDetailResponseModel userReturnValue = new UserDetailResponseModel();

		UserDetailDto userDto = new UserDetailDto();

		// BeanUtils.copyProperties(source, target) Copy the property values of the
		// given source bean into the target bean.
		// Note: The source and target classes do not have to match or even be derived
		// from each other, as long as the properties match.
		// Any bean properties that the source bean exposes but the target bean does not
		// will silently be ignored.
		BeanUtils.copyProperties(userDetailModel, userDto);

		UserDetailDto createdUser = userService.createUser(userDto);

		BeanUtils.copyProperties(createdUser, userReturnValue);

		return userReturnValue;
	}
	
	
	
	@GetMapping("/{id}")
	public String getUser(@PathVariable String id) {
		
		UserDetailDto userDto = userService.getUserByUserId(id);
		
		return "Get User was called";
	}

	
	

	@PutMapping
	public String updateUser() {
		return "Update User was called";
	}

	@DeleteMapping
	public String deleteUser() {
		return "Delete User was called";
	}

	//################# END USER FUNCTIONAL METHODS ##########################	

	
}