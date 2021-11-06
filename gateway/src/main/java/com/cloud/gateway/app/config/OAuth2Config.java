package com.cloud.gateway.app.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

@Configuration
public class OAuth2Config {

    @Bean("oauthAuthenticationWebFilter")
    AuthenticationWebFilter oauthAuthenticationWebFilter(
            OAuth2AuthenticationManagerAdapter authManager, OAuthTokenConverter tokenConverter) {

        AuthenticationWebFilter filter = new AuthenticationWebFilter(authManager);
        filter.setAuthenticationConverter(tokenConverter);
        return filter;
    }

    @Bean
    ResourceServerTokenServices tokenServices(Builder webClientBuilder) {
//        DefaultTokenServices tokenServices = new DefaultTokenServices();
    	CloudRemoteTokenServices tokenServices = new CloudRemoteTokenServices(webClientBuilder); 
    	tokenServices.setClientId("sherkat55");
    	tokenServices.setClientSecret("sherkat55");
//    	tokenServices.setCheckTokenEndpointUrl("");
        return tokenServices;
    }
    
    @Bean
    @LoadBalanced
    WebClient.Builder builder() {
        return WebClient.builder();
    }
    

}