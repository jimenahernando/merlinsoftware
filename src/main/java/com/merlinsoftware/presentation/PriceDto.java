package com.merlinsoftware.presentation;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PriceDto {

	private Long brandId;
	private Long productId;
	private LocalDateTime applicationDate;
	private Double price;
}
