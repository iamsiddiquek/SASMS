package com.sasms.io.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sasms.io.entity.UserEntity;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
//	CrudRepository<T, ID> --"T" is for Entity Class and "ID" is generated ID Long

	UserEntity findByEmail(String email);

	
	
}
