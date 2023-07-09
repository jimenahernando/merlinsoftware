package com.merlinsoftware.test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.merlinsoftware.application.PriceService;
import com.merlinsoftware.application.PriceServiceImpl;
import com.merlinsoftware.application.mapper.PriceMapper;
import com.merlinsoftware.domain.Prices;
import com.merlinsoftware.domain.PricesRepository;
import com.merlinsoftware.infraestructure.PriceNotFoundException;
import com.merlinsoftware.presentation.PriceDto;

@ExtendWith(SpringExtension.class)
public class PriceServiceImplTest {

	@Mock
	private PricesRepository repository;
	
	@Mock 
	private PriceMapper priceMapper;
	
	@InjectMocks
	private PriceServiceImpl priceService;
	
	List<Prices> prices;
	Prices price;
	List<PriceDto> pricesDto;
	PriceDto dto;
	
	@BeforeEach
	void setup() {
		pricesDto = new ArrayList<>();
		dto = new PriceDto();
		prices = new ArrayList<>();
		price = new Prices();
	}

	@AfterEach
	void clean(){
		pricesDto = null;
		price = null;
		price = null;
		dto = null;
	}
	
	@Test
	void getAllPrices_whenCalled_returnOk() {
		prices.add(price);
		pricesDto.add(dto);
		when(repository.findAll()).thenReturn(prices);
		when(priceMapper.convertToDtoList(prices)).thenReturn(pricesDto);
		
		List<PriceDto> response = priceService.getAllPrices();

		assertNotNull(response);
		assertEquals(1, response.size());
	}
	
	@Test
	void getAllPrices_whenEmpty_returnException() {
		when(repository.findAll()).thenReturn(Arrays.asList());
		
		Exception exception = assertThrows(PriceNotFoundException.class, () -> {
			priceService.getAllPrices();
	    });
		
	    assertTrue(exception.getMessage().contains("vacio"));
	}
	
	@Test
	void getPricesByApplicationDate_whenEmpty_returnException() {
		when(repository.findAll()).thenReturn(null);
		
		Exception exception = assertThrows(PriceNotFoundException.class, () -> {
			priceService.getPricesByApplicationDate(dto);
	    });
		
	    assertTrue(exception.getMessage().contains("No existe ningun precio"));
	}
	
	@Test
	void getPricesByApplicationDate_whenOnePrice_returnOk() {
		price.setPrice(10D);
		prices.add(price);
		pricesDto.add(dto);
		dto.setPrice(10D);
		when(repository.findAllbyDate(any(), any(), any())).thenReturn(prices);
		when(priceMapper.convertToDto(price)).thenReturn(dto);
		
		PriceDto response = priceService.getPricesByApplicationDate(dto);

		assertNotNull(response);
		assertEquals(price.getPrice(), response.getPrice());
	}
	
	@Test
	void getPricesByApplicationDate_whenMorePrices_returnOk() {
		Prices price2 = new Prices();
		price2.setPriority(3L);
		price2.setPrice(15D);
		
		price.setPriority(1L);
		price.setPrice(33D);
		
		prices.add(price);
		prices.add(price2);
		pricesDto.add(dto);
		
		dto.setPrice(15D);
		
		when(repository.findAllbyDate(any(), any(), any())).thenReturn(prices);
		when(priceMapper.convertToDto(any(Prices.class))).thenReturn(dto);
		
		PriceDto response = priceService.getPricesByApplicationDate(dto);

		assertNotNull(response);
		assertEquals(price2.getPrice(), response.getPrice());
	}
}
