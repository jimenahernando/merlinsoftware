package com.merlinsoftware.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.nio.file.Files;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import com.merlinsoftware.domain.PricesRepository;
import com.merlinsoftware.infraestructure.PriceNotFoundException;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SqlGroup({ @Sql(value = "classpath:empty/reset.sql", executionPhase = BEFORE_TEST_METHOD),
@Sql(value = "classpath:init/price-data.sql", executionPhase = BEFORE_TEST_METHOD) })
class PriceTestIntegration {

	@Autowired
	private PricesRepository repository;

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void getAllPrices() throws Exception {
		this.mockMvc.perform(get("/api/prices"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON))
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$", hasSize(4)))
			.andExpect(jsonPath("$.[0].brandId").value(1));

		assertThat(this.repository.findAll()).hasSize(4);

	}

	@Test
	void getPrice_whenCalled_returnException() throws Exception {
		final File jsonFile = new ClassPathResource("init/price_not_found.json").getFile();
		final String priceFound = Files.readString(jsonFile.toPath());

		this.mockMvc.perform(post("/api/prices")
			.contentType(APPLICATION_JSON)
			.content(priceFound))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof PriceNotFoundException))
			.andExpect(result -> assertEquals("No existe ningun precio dentro de ese rango de tiempo",
					result.getResolvedException().getMessage()));

	}
	
	@ParameterizedTest
	@CsvSource({ "0, 35.50", "1, 25.45" , "2, 35.50" , "3, 30.50" , "4, 38.95" })
	void getPrice_whenTest1_return_ok(int position, double price) throws Exception {
		final File jsonFile = new ClassPathResource("init/price.json").getFile();
		final String priceFound = Files.readString(jsonFile.toPath());
		
		JSONArray json = new JSONArray(priceFound);
		JSONObject objeto = json.getJSONObject(position);

		this.mockMvc.perform(post("/api/prices")
			.contentType(APPLICATION_JSON)
			.content(objeto.toString()))
			.andDo(print())
			.andExpect(status().isOk())
            .andExpect(jsonPath("$").isMap())
            .andExpect(jsonPath("$", aMapWithSize(4)))
			.andExpect(jsonPath("$.brandId").value(1))
			.andExpect(jsonPath("$.productId").value(35455))
			.andExpect(jsonPath("$.price").value(price));

	}
}
