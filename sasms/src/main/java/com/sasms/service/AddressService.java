package com.sasms.service;

import java.util.List;

import com.sasms.shared.dto.AddressDto;

public interface AddressService {

	List<AddressDto> getAddresses(String id);

	AddressDto getUserAddress(String addressId);
	
}
