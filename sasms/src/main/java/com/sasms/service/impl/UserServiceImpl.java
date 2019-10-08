package com.sasms.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sasms.io.entity.RoleEntity;
import com.sasms.io.entity.UserEntity;
import com.sasms.io.repositories.RoleRepository;
import com.sasms.io.repositories.UserRepository;
import com.sasms.service.UserService;
import com.sasms.shared.Utils;
import com.sasms.shared.dto.UserDetailDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private Utils utils;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Override
	public UserDetailDto createUser(UserDetailDto userDetailDto) {
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userDetailDto, userEntity);
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetailDto.getPassword()));
		userEntity.setUserId(utils.generateUserId(30));
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
		UserEntity userEntity = userRepository.findByEmail(email);
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for (RoleEntity role : userEntity.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		}				
		if(userEntity == null) throw new UsernameNotFoundException(email);
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), grantedAuthorities);
	}

	
	@Override
	public UserDetailDto getUserByEmail(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		if(userEntity == null) throw new UsernameNotFoundException(email);
		UserDetailDto returnedValue = new UserDetailDto();
		BeanUtils.copyProperties(userEntity, returnedValue);		
		return returnedValue;
	}

	
	@Override
	public void save(UserDetailDto userDetailDto) {
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userDetailDto, userEntity);
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetailDto.getPassword()));
		userEntity.setUserId(utils.generateUserId(30));
		userEntity.setRoles(new HashSet<>(roleRepository.findAll()));
		userRepository.save(userEntity);		
	}

	@Override
	public UserDetailDto getUserByUserId(String id) {

		UserEntity userEntity = userRepository.findByUserId(id);
		UserDetailDto userDto = new UserDetailDto();
		BeanUtils.copyProperties(userEntity, userDto);

		return userDto;
	}

}
