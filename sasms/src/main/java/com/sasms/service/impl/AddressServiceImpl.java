package com.sasms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sasms.io.entity.AddressEntity;
import com.sasms.io.entity.UserEntity;
import com.sasms.io.repositories.AddressRepository;
import com.sasms.io.repositories.UserRepository;
import com.sasms.service.AddressService;
import com.sasms.shared.dto.AddressDto;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	ModelMapper modelMapper = new ModelMapper();
	
	@Override
	public List<AddressDto> getAddresses(String userId) {
		UserEntity userEntity = userRepository.findUserByUserId(userId);		
		List<AddressDto> returnValue = new ArrayList<>();
		if(userEntity == null) return returnValue;
		
		Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);
		for (AddressEntity addressEntity : addresses) {
			returnValue.add(modelMapper.map(addressEntity, AddressDto.class));
		}		
		return returnValue;
	}

	@Override
	public AddressDto getUserAddress(String addressId) {
		AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
		AddressDto addressDto = modelMapper.map(addressEntity, AddressDto.class);
		return addressDto;
	}

}
