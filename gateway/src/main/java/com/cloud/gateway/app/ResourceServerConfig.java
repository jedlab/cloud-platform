package com.cloud.gateway.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId("resource-id").tokenServices(null);
	}
	
	
	@Bean
	@Primary
	ResourceServerTokenServices tokenService()
	{
		return new ResourceServerTokenServices() {
			
			@Override
			public OAuth2AccessToken readAccessToken(String accessToken) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public OAuth2Authentication loadAuthentication(String accessToken)
					throws AuthenticationException, InvalidTokenException {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().authorizeRequests()
		.antMatchers(HttpMethod.OPTIONS, "/*","/**")
		.permitAll()
		.antMatchers("/*-service/**").denyAll()
		.antMatchers("/oauth/**").permitAll()
		.and()
		.csrf().disable();
	}
	
}
