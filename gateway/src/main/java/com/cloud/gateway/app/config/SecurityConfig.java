package com.cloud.gateway.app.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
	
	
	static List<String> permitURI = new ArrayList<>();
	
	static {
		permitURI.add("/swagger-ui.html");
		permitURI.add("/configuration/ui");
		permitURI.add("/swagger-resources/**");
		permitURI.add("/configuration/security");
		permitURI.add("/webjars/*");
		permitURI.add("/webjars/**");
		permitURI.add("/*/api-docs");
		permitURI.add("/*/api-docs/**");
		permitURI.add("/user/v3/api-docs");
		permitURI.add("/auth/v3/api-docs");
		permitURI.add("/auth/v1/token");
	}

	/**
     * The configuration defined here is what really drives the edge service.
     * Any request that doesn't get handled by the other two configs will be
     * handled by this one. All requests coming through here must have a
     * valid access token
     */
    @Bean
    SecurityWebFilterChain oauthTokenAuthConfig(
            ServerHttpSecurity security, AuthenticationWebFilter oauthAuthenticationWebFilter, RouteDefinitionLocator locator,
            CorsWebFilter cors) {

    	
    	List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
    	
        return security
                .csrf().disable()
                .logout().disable()
                .httpBasic().disable()
                .formLogin().disable()
//                .cors().and()
                .exceptionHandling().and()
//                .securityMatcher(notMatches(permitURI.toArray(new String[permitURI.size()])))
                .addFilterBefore(cors, SecurityWebFiltersOrder.HTTP_BASIC)
                .addFilterAt(oauthAuthenticationWebFilter, SecurityWebFiltersOrder.HTTP_BASIC)
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS, "**").permitAll()
                .matchers(matches(permitURI.toArray(new String[permitURI.size()]))).permitAll()
                .matchers(matches("/user/api/**", "/auth/api/**", "/api/**"))
                .authenticated()
                .and()
                .build();
    }
    
    @Bean
    public CorsWebFilter corsWebFilter() {

        final CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(Collections.singletonList("*"));
//        corsConfig.setMaxAge(3600L);
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PUT"));
        corsConfig.addAllowedHeader("*");
        corsConfig.setAllowCredentials(false);

        //
        Map<String, CorsConfiguration> configMap = new HashMap<>();
        configMap.put("/**", corsConfig);
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.setCorsConfigurations(configMap);

        return new CorsWebFilter(source);
    }  

    private ServerWebExchangeMatcher matches(String ... routes) {
        return ServerWebExchangeMatchers.pathMatchers(routes);
    }

    private ServerWebExchangeMatcher notMatches(String ... routes) {
        return new NegatedServerWebExchangeMatcher(matches(routes));
    }
}