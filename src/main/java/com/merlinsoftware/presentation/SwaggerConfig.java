package com.merlinsoftware.presentation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI info() {
		Contact contact = new Contact();
		contact.setName("Ma Jimena Hernando Barreira");
		contact.setEmail("jimenahernando@hotmail.com");
		contact.setUrl("https://github.com/jimenahernando/merlinsoftware/tree/main");

		Info info = new Info().title("Merlin Software API").version("1.0").contact(contact).description("Prices");

		return new OpenAPI().info(info);
	}
}
