package com.coderkan.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "wallet", schema = "public")
public class Wallet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private long id;
	@Column(name = "name")
	private String name;
	@OneToOne
	@JoinColumn(name = "owner", referencedColumnName = "id")
	private User owner;
	@Column(name = "amount")
	private int amount;
	@OneToOne
	@JoinColumn(name = "asset", referencedColumnName = "id")
	private Asset asset;

}
