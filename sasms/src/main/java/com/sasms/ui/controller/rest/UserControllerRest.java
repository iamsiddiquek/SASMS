package com.sasms.ui.controller.rest;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sasms.enums.ErrorMessages;
import com.sasms.enums.RequestOperationName;
import com.sasms.enums.RequestOperationStatus;
import com.sasms.exceptions.UserServiceException;
import com.sasms.io.repositories.UserRepository;
import com.sasms.service.UserService;
import com.sasms.shared.dto.UserDetailDto;
import com.sasms.ui.model.request.UserRequestModel;
import com.sasms.ui.model.responce.OperationStatusModel;
import com.sasms.ui.model.responce.UserResponseModel;

@RestController
@RequestMapping("/users")
public class UserControllerRest {

	// ################# START USER INJECTED BEANS ##########################
	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	private ModelMapper modelMapper = new ModelMapper();

	
	// ################# END USER INJECTED BEANS ##########################
	// ################# START USER FUNCTIONAL METHODS ##########################

	// @RequestBody parameter should be bound to the body of the web request class.
	// The body of the request uses HttpMessageConverter to resolve
	// the method argument depending on the content type of the request.
	// @Valid Validation can be applied by annotating @Valid.
	@PostMapping(
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public UserResponseModel createUser(@RequestBody UserRequestModel userModel) {

		if (userRepository.findUserByEmail(userModel.getEmail()) != null)
			throw new RuntimeException("Email is already present into database.");

//		boolean userExist = userService.checkUserByEmail(userModel.getEmail());
//		if(!userExist) 

		if (userModel.getFirstName().isEmpty()) {
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		}
		// BeanUtils.copyProperties(source, target) Copy the property values of source
		// bean into the target bean.
		// Not matched properties will be ignored silently.
//		BeanUtils.copyProperties(userModel, userDto);
//		BeanUtils.copyProperties(createdUser, userReturnValue);

		UserDetailDto userDto = modelMapper.map(userModel, UserDetailDto.class);
		UserDetailDto createdUser = userService.createUser(userDto);
		UserResponseModel userReturnValue  = modelMapper.map(createdUser, UserResponseModel.class);

		return userReturnValue;
	}

//	ResponseEntity is meant to represent the entire HTTP response. 
//	You can control anything that goes into it: status code, headers, and body.
	@GetMapping(value = "/{id}", 
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<UserResponseModel> getUser(@PathVariable String id) {

		UserDetailDto userDto = userService.getUserByUserId(id);
		UserResponseModel userReturnValue = modelMapper.map(userDto, UserResponseModel.class);
//		BeanUtils.copyProperties(userDto, userReturnValue);
		return new ResponseEntity<UserResponseModel>(userReturnValue, HttpStatus.OK);
	}

	@PutMapping(path = "/{id}", 
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
			consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public UserResponseModel updateUser(@PathVariable String id, @RequestBody UserRequestModel userRequestModel) {
		UserDetailDto userDto = new UserDetailDto();
		BeanUtils.copyProperties(userRequestModel, userDto);
		UserDetailDto updatedUser = userService.updateUser(id, userDto);
		UserResponseModel returnModel = new UserResponseModel();
		BeanUtils.copyProperties(updatedUser, returnModel);
		return returnModel;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteUser(@PathVariable String id) {
		OperationStatusModel operationStatus = new OperationStatusModel();
		operationStatus.setOperationName(RequestOperationName.DELETE.name());
		operationStatus.setOperationResult(RequestOperationStatus.SUCCESS.name());
		userService.deleteUser(id);
		return operationStatus;
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<UserResponseModel>> getAllUsers(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "25") int limit) {
		List<UserDetailDto> users = userService.getUsers(page, limit);
		List<UserResponseModel> returnUserList = new ArrayList<>();
		for (UserDetailDto dto : users) {
			UserResponseModel model = new UserResponseModel();
			BeanUtils.copyProperties(dto, model);
			returnUserList.add(model);
		}
		return new ResponseEntity<List<UserResponseModel>> (returnUserList, HttpStatus.OK);
	}
	
	// ################# END USER FUNCTIONAL METHODS ##########################

}