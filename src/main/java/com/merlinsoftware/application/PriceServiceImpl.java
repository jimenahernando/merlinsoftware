package com.merlinsoftware.application;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.merlinsoftware.application.mapper.PriceMapper;
import com.merlinsoftware.domain.Prices;
import com.merlinsoftware.domain.PricesRepository;
import com.merlinsoftware.infraestructure.PriceNotFoundException;
import com.merlinsoftware.presentation.PriceDto;

@Service
public class PriceServiceImpl implements PriceService {
	
	@Autowired
	private PricesRepository priceRepository;

	@Autowired
	private PriceMapper priceMapper;

	@Override
	public PriceDto getPricesByApplicationDate(PriceDto dto) {
		Prices price = null;
		List<Prices> prices = priceRepository.findAllbyDate(dto.getApplicationDate(), dto.getBrandId(), dto.getProductId());
		if(prices == null || prices.isEmpty()) {
			throw new PriceNotFoundException(HttpStatus.BAD_REQUEST.toString(),"No existe ningun precio dentro de ese rango de tiempo");
		}

		if(prices.size() == 1)
			price = prices.get(0);
		
		if(prices.size() > 1)
			price = prices
		      .stream()
		      .max(Comparator.comparing(Prices::getPriority))
		      .orElse(null);
		
		return priceMapper.convertToDto(price);
	}

	@Override
	public List<PriceDto> getAllPrices() {
		List<Prices> prices = priceRepository.findAll();
		if(prices.isEmpty()) {
			throw new PriceNotFoundException(HttpStatus.BAD_REQUEST.toString(), "El listado de precios está vacio");
		}
		
		return priceMapper.convertToDtoList(prices);
	}
}
