package com.coderkan.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "operation", schema = "public")
public class Operation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private long id;
	@OneToOne
	@JoinColumn(name = "sourceWallet", referencedColumnName = "id")
	private Wallet sourceWallet;
	@OneToOne
	@JoinColumn(name = "targetWallet", referencedColumnName = "id")
	private Wallet targetWallet;
	@OneToOne
	@JoinColumn(name = "assetWallet", referencedColumnName = "id")
	private Asset asset;
	@Column(name = "amount")
	private int amount;

}
