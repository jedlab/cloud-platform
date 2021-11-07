package com.cloud.userservice.app.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
@OpenAPIDefinition(info = @Info(title = "User API", version = "1.0", description = "Documentation User API v1.0"))
public class OpenApiSwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI(@Value("${springdoc.version:1.5.11}") String appVersion) {
		Components comp = new Components();
		Map<String, SecurityScheme> securitySchemes = new HashMap<>();
		comp.addSecuritySchemes("bearer-jwt", new SecurityScheme()
				.name("Authorization").type(SecurityScheme.Type.HTTP).scheme("bearer")
				.bearerFormat("JWT")				
				.in(SecurityScheme.In.HEADER));
		
		comp.addSecuritySchemes("bearer-jwt", new SecurityScheme()
				.name("Authorization").type(SecurityScheme.Type.HTTP).scheme("basic")
				.bearerFormat("JWT")				
				.in(SecurityScheme.In.HEADER));
		return new OpenAPI().components(comp)
				.addServersItem(new Server().url("http://localhost:1443/user"))
				.info(new io.swagger.v3.oas.models.info.Info().title("User API").version(appVersion)
						.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}

}
