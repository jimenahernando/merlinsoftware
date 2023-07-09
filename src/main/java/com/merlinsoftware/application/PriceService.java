package com.merlinsoftware.application;

import java.util.List;

import com.merlinsoftware.presentation.PriceDto;

public interface PriceService {
	
	PriceDto getPricesByApplicationDate(PriceDto dto);
	
	List<PriceDto> getAllPrices();

}
