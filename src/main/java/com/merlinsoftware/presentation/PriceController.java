package com.merlinsoftware.presentation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.merlinsoftware.application.PriceService;
import com.merlinsoftware.infraestructure.PriceNotFoundException;

@RestController
@RequestMapping("/api/price")
public class PriceController {

	@Autowired
	private PriceService priceService;

	@PostMapping
	public ResponseEntity<PriceDto> getProductByIdAndPriceList(@RequestBody PriceDto dto) {
		return new ResponseEntity<>(priceService.getPricesByApplicationDate(dto), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<PriceDto>> getAllPrices() {
		return new ResponseEntity<>(priceService.getAllPrices(), HttpStatus.OK);
	}

	@ControllerAdvice
	public class GlobalExceptionHandler {
		@ExceptionHandler(PriceNotFoundException.class)
		public ResponseEntity<String> handleProductNotFoundException(PriceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
}
