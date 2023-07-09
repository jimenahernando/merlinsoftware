package com.merlinsoftware.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PricesRepository extends JpaRepository<Prices, Long>{
	
	@Query(value ="select p from Prices p where (:applicationDate >= p.startDate and :applicationDate <= p.endDate) and (p.brandId = :brandId) and (p.productId = :productId)")
	List<Prices> findAllbyDate(@Param("applicationDate") LocalDateTime applicationDate, @Param("brandId") Long brandId, @Param("productId") Long productId);
}
