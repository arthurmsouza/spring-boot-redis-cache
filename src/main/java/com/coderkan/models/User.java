package com.coderkan.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user", schema = "public")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private long id;
	@Column(name = "name")
	private String name;
	@Column(name = "address")
	private String address;

	@Column(name = "city")
	private String city;

	@Column(name = "postalCode")
	private String postalCode;

	@Column(name = "country")
	private String country;

}
