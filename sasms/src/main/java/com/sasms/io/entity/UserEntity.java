package com.sasms.io.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity()
public class UserEntity extends CommonEntity implements Serializable{
	
	@Setter(value = AccessLevel.NONE)
	@Getter(value = AccessLevel.NONE)
	private static final long serialVersionUID = -4091245409341767321L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 50)
	private String userId;

	@Column(nullable = false, length = 50)
	private String firstName;

	@Column(nullable = false, length = 50)
	private String lastName;
	
	@Column(nullable = false, length = 150, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String encryptedPassword;
	
	private String emailVarificationToken;

	@Column(nullable = false, columnDefinition = "boolean default false")
	private Boolean emailVarificationStatus;
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<RoleEntity> roles;
	
	
//	Specifies a collection of instances of a basic type or embeddable class.
//	Must be specified if the collection is to be mapped by means of a collection table. 
//	@ElementCollection(fetch = FetchType.EAGER)
	
	@OneToMany(mappedBy="userDetails", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	private List<AddressEntity> addresses;
	
	
}