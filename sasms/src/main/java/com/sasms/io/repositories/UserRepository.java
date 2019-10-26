package com.sasms.io.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sasms.io.entity.UserEntity;


@Transactional
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
//	CrudRepository<T, ID> --"T" is for Entity Class and "ID" is generated ID Long

	UserEntity findUserByEmail(String email);

	UserEntity findUserByUserId(String id);
	
	// Native Query Language
	@Query(value="select * from users u where u.EMAIL_VARIFICATION_STATUS = :true", nativeQuery = true,
			countQuery = "select count(*) from users u where u.EMAIL_VARIFICATION_STATUS = :true")
	Page<UserEntity> findAllUsersWithVarifiedEmailAddress(Pageable pageableRequest);
	
	@Query(value="select * from users u where u.first_Name = ?1", nativeQuery = true)
	List<UserEntity> findUserByFirstName(String firstName);
	
	@Query(value = "select * from users u where u.last_name =:lastName", nativeQuery = true)
	List<UserEntity> findUserByLastName(@Param("lastName") String lastName);
	
	// Queries that require a `@Modifying` annotation include INSERT, UPDATE, DELETE, and DDLstatements
	@Modifying
	@Query(value = "update users u set u.email_Varification_Status =:emailVarificationStatus where u.user_id =:userId", nativeQuery = true)
	void updateUserEmailStatusByUserId(@Param("emailVarificationStatus") Boolean emailVarificationStatus, @Param("userId") String userId);
	
	// JPQL Java Persistence Query Language
	@Query(value="select u from UserEntity u where u.userId =:userId")
	List<UserEntity> findUserEntityByUserId(@Param("userId") String userId);

		
}
