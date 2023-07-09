package com.merlinsoftware.application.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.merlinsoftware.domain.Prices;
import com.merlinsoftware.presentation.PriceDto;

@Component
public class PriceMapper extends BaseMapper<Prices, PriceDto>{

	@Override
	public Prices convertToEntity(PriceDto dto, Object... args) {
		Prices entity = new Prices();
		if (dto != null) {
			BeanUtils.copyProperties(dto, entity);
		}
		return entity;
	}

	@Override
	public PriceDto convertToDto(Prices entity, Object... args) {
		PriceDto dto = new PriceDto();
		if (entity != null) {
			BeanUtils.copyProperties(entity, dto);
		}
		return dto;
	}

}
