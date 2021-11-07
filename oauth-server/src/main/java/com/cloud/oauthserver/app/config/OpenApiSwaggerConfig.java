package com.cloud.oauthserver.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Auth API", version = "1.0", description = "Documentation Auth API v1.0"))
public class OpenApiSwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI(@Value("${springdoc.version:1.5.11}") String appVersion) {
		Components comp = new Components();
		comp.addSecuritySchemes("bearer-jwt", new SecurityScheme().name("Authorization")
				.type(SecurityScheme.Type.APIKEY).scheme("bearer").bearerFormat("JWT").in(SecurityScheme.In.HEADER));
		comp.addSecuritySchemes("contentType", new SecurityScheme().name("Content-Type")
				.type(SecurityScheme.Type.APIKEY).scheme("http").in(SecurityScheme.In.HEADER));
		return new OpenAPI().components(comp).addServersItem(new Server().url("http://localhost:8787"))
				.addSecurityItem(new SecurityRequirement().addList("bearer-jwt").addList("contentType"))
				.info(new io.swagger.v3.oas.models.info.Info().title("Auth API").version(appVersion)
						.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}

}
