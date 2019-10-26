package com.sasms.io.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="addresses")
public class AddressEntity extends CommonEntity implements Serializable {

	@Setter(value = AccessLevel.NONE)
	@Getter(value = AccessLevel.NONE)
	private static final long serialVersionUID = -1970597604691083655L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long   id;

	@Column(name = "address_id", nullable = false, length = 30)
	private String addressId;

	@Column(name = "city", length = 30)
	private String city;

	@Column(name = "country", length = 30)
	private String country;
	
	@Column(name = "street_name", length = 100)
	private String streetName;
	
	@Column(name = "postal_code", length = 10)
	private String postalCode; 
	
	@Column(name = "type", length = 20)
	private String type;

	@ManyToOne
	@JoinColumn(name = "users_id")
	private UserEntity userDetails;
}
