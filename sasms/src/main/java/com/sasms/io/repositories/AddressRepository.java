package com.sasms.io.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sasms.io.entity.AddressEntity;
import com.sasms.io.entity.UserEntity;

@Transactional
@Repository
public interface AddressRepository  extends JpaRepository<AddressEntity, Long>{

	List<AddressEntity> findAllByUserDetails(UserEntity userEntity);

	AddressEntity findByAddressId(String addressId);

	
}
