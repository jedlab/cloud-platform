package com.cloud.gateway.app.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiSwaggerConfig {

	@Bean
	@Lazy(false)
	public List<GroupedOpenApi> apis(SwaggerUiConfigParameters swaggerUiConfigParameters,
			RouteDefinitionLocator locator) {
		List<GroupedOpenApi> groups = new ArrayList<>();
		List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
		for (RouteDefinition definition : definitions) {
			System.out.println("id: " + definition.getId() + "  " + definition.getUri().toString());
		}
		definitions.stream().filter(routeDefinition -> routeDefinition.getId().matches(".*-service")
				|| routeDefinition.getId().matches(".*-server")).forEach(routeDefinition -> {
					String name = routeDefinition.getId().replaceAll("-service", "").replaceAll("-server", "");
					swaggerUiConfigParameters.addGroup(name);
					groups.add(GroupedOpenApi.builder().pathsToMatch("/" + name + "/**").group(name).build());
				});
		return groups;
	}

	@Bean
	public OpenAPI customOpenAPI(@Value("${springdoc.version:1.5.11}") String appVersion,
			RouteDefinitionLocator locator, @Value("${eureka.instance.hostname:localhost}") String hostname,
			@Value("${server.port:1443}") String port) {
		Components comp = new Components();
		comp.addSecuritySchemes("bearer-jwt", new SecurityScheme().name("Authorization")
				.type(SecurityScheme.Type.APIKEY).scheme("bearer").bearerFormat("JWT").in(SecurityScheme.In.HEADER));
		//
		List<Server> servers = new ArrayList<>();
		List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
		definitions.stream().filter(routeDefinition -> routeDefinition.getId().matches(".*-service")
				|| routeDefinition.getId().matches(".*-server")).forEach(routeDefinition -> {
					String name = routeDefinition.getId().replaceAll("-service", "").replaceAll("-server", "");
					servers.add(new Server().url(hostname + "/" + name));
					System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
					System.out.println("http://"+hostname + ":" + port + "/" + name);
				});

		OpenAPI openAPI = new OpenAPI().components(comp)
				.addSecurityItem(new SecurityRequirement().addList("bearer-jwt")).servers(servers)
				.info(new io.swagger.v3.oas.models.info.Info().title("Gateway API").version(appVersion)
						.license(new License().name("Apache 2.0").url("http://springdoc.org")));
		return openAPI;
	}

}
