package com.sasms.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sasms.enums.ErrorMessages;
import com.sasms.exceptions.UserServiceException;
import com.sasms.io.entity.RoleEntity;
import com.sasms.io.entity.UserEntity;
import com.sasms.io.repositories.RoleRepository;
import com.sasms.io.repositories.UserRepository;
import com.sasms.service.UserService;
import com.sasms.shared.Utils;
import com.sasms.shared.dto.UserDetailDto;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private Utils utils;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	
	@Override
	public UserDetailDto createUser(UserDetailDto userDetailDto) {
		UserEntity userEntity = modelMapper.map(userDetailDto, UserEntity.class);
//		BeanUtils.copyProperties(userDetailDto, userEntity);
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetailDto.getPassword()));
		userEntity.setUserId(utils.generateRandomPublicId(30));
		for (int i=0; i<userEntity.getAddresses().size(); i++) {
			userEntity.getAddresses().get(i).setAddressId(utils.generateRandomPublicId(30));
		}
		UserEntity storedUserDetails = userRepository.save(userEntity);		
		UserDetailDto returnUserDetailDto = new UserDetailDto();
		BeanUtils.copyProperties(storedUserDetails, returnUserDetailDto);		
		return returnUserDetailDto;
	}

	// This method is provided by the *UserDetailsService* interface which is provided by the spring
	// this will fetch user entity from DB and with their roles/authorities and allocate to the *User*
	// class which is provided by the Spring. it has multiple Constructors to provide features of it.
	
	@SuppressWarnings("unused")
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findUserByEmail(email);
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for (RoleEntity role : userEntity.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		}				
		if(userEntity == null) throw new UsernameNotFoundException(email);
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), grantedAuthorities);
	}

	
	@Override
	public UserDetailDto getUserByEmail(String email) {
		UserEntity userEntity = userRepository.findUserByEmail(email);
		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		UserDetailDto returnedValue = new UserDetailDto();
		BeanUtils.copyProperties(userEntity, returnedValue);		
		return returnedValue;
	}

	
	@Override
	public void save(UserDetailDto userDetailDto) {
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userDetailDto, userEntity);
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetailDto.getPassword()));
		userEntity.setUserId(utils.generateRandomPublicId(30));
		userEntity.setRoles(new HashSet<>(roleRepository.findAll()));
		userRepository.save(userEntity);		
	}

	@Override
	public UserDetailDto getUserByUserId(String userId) {
		UserEntity userEntity = userRepository.findUserByUserId(userId);		
		if(userEntity==null) throw new UsernameNotFoundException(userId);
		UserDetailDto userDto = new UserDetailDto();
		BeanUtils.copyProperties(userEntity, userDto);
		return userDto;
	}

	@Override
	public UserDetailDto updateUser(String id, UserDetailDto userDto) {
		UserEntity fetchEntity = userRepository.findUserByUserId(id);
		if(fetchEntity==null) throw new UsernameNotFoundException(id);
		
		if(!userDto.getFirstName().isEmpty()) {
			fetchEntity.setFirstName(userDto.getFirstName());
		}
		if(!userDto.getLastName().isEmpty()) {
			fetchEntity.setLastName(userDto.getLastName());
		}
		UserEntity returnEntity = userRepository.save(fetchEntity);
		UserDetailDto returnDto = new UserDetailDto();
		BeanUtils.copyProperties(returnEntity, returnDto);
		return returnDto;
	}

	@Override
	public boolean checkUserByEmail(String email) {

		if (userRepository.findUserByEmail(email) != null) {
			throw new RuntimeException("Email is already present into database.");
		}
		return false;
	}

	@Override
	public void deleteUser(String id) {
		UserEntity userEntity = userRepository.findUserByUserId(id);
		if (userEntity != null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
	
		userRepository.delete(userEntity);
	}

	@Override
	public List<UserDetailDto> getUsers(int page, int limit) {
		if (page > 0)
			page -= 1;
		Pageable pageable = PageRequest.of(page, limit);
		Page<UserEntity> users = userRepository.findAll(pageable);
		List<UserEntity> returnUsers = new ArrayList<>();
		returnUsers = users.getContent();
		List<UserDetailDto> returnUsersDto = new ArrayList<>();
		for (UserEntity entity : returnUsers) {
			UserDetailDto userDto = new UserDetailDto();
			BeanUtils.copyProperties(entity, userDto);
			returnUsersDto.add(userDto);
		}
		return returnUsersDto;
	}

}
