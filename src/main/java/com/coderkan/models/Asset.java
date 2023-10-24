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
@Table(name = "asset", schema = "public")
public class Asset {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private long id;
	@Column(name = "name")
	private String name;
	@Column(name = "symbol")
	private String symbol;
	@Column(name = "usdPrice")
	private BigDecimal usdPrice;


}
