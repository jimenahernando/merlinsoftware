package com.merlinsoftware.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "PRICES")
public class Prices {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	@Column(name = "ID", unique = true, nullable = false, precision = 21, scale = 0)
	private Long priceId;
	
	@Column(name = "BRAND_ID", nullable = false, precision = 21, scale = 0)
	private Long brandId;
	
	@Column(name = "START_DATE")
	private LocalDateTime startDate;
	
	@Column(name = "END_DATE")
	private LocalDateTime endDate;
	
	@Column(name = "PRICE_LIST")
	private Long priceList;
	
	@Column(name = "PRODUCT_ID", nullable = false, precision = 21, scale = 0)
	private Long productId;
	
	@Column(name = "PRIORITY")
	private Long priority;
	
	@Column(name = "PRICE")
	private Double price;
	
	@Column(name = "CURR")
	private String curr;
	
}
