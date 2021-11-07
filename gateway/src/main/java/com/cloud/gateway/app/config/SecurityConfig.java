package com.cloud.gateway.app.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

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
	}

	/**
     * The configuration defined here is what really drives the edge service.
     * Any request that doesn't get handled by the other two configs will be
     * handled by this one. All requests coming through here must have a
     * valid access token
     */
    @Bean
    SecurityWebFilterChain oauthTokenAuthConfig(
            ServerHttpSecurity security, AuthenticationWebFilter oauthAuthenticationWebFilter, RouteDefinitionLocator locator) {

    	
    	List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
    	
        return security
                .csrf().disable()
                .logout().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .exceptionHandling().and()
//                .securityMatcher(notMatches(permitURI.toArray(new String[permitURI.size()])))                
                .addFilterAt(oauthAuthenticationWebFilter, SecurityWebFiltersOrder.HTTP_BASIC)
//                .authorizeExchange()
//                .matchers(matches(permitURI.toArray(new String[permitURI.size()]))).ig
//                .matchers(matches("/user/**", "/auth/**"))
//                .permitAll()
//                .authenticated()
//                .and()
                .build();
    }

    private ServerWebExchangeMatcher matches(String ... routes) {
        return ServerWebExchangeMatchers.pathMatchers(routes);
    }

    private ServerWebExchangeMatcher notMatches(String ... routes) {
        return new NegatedServerWebExchangeMatcher(matches(routes));
    }
}