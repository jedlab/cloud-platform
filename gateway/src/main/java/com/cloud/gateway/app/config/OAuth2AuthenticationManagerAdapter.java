package com.cloud.gateway.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
public class OAuth2AuthenticationManagerAdapter implements ReactiveAuthenticationManager {

    private final AuthenticationManager authenticationManager;

    @Autowired
    public OAuth2AuthenticationManagerAdapter(ResourceServerTokenServices tokenServices) {
        this.authenticationManager = oauthManager(tokenServices);
    }

    public Mono<Authentication> authenticate(Authentication token) {
        return Mono.just(token).publishOn(Schedulers.elastic()).flatMap(t -> {
            try {
                return Mono.just(this.authenticationManager.authenticate(t));
            } catch (Exception x) {
                return Mono.error(new BadCredentialsException("Invalid or expired access token presented"));
            }
        }).filter(Authentication::isAuthenticated);
    }

    private AuthenticationManager oauthManager(ResourceServerTokenServices tokenServices) {
        OAuth2AuthenticationManager oauthAuthenticationManager = new OAuth2AuthenticationManager();
        oauthAuthenticationManager.setResourceId("gateway");
        oauthAuthenticationManager.setTokenServices(tokenServices);
        return oauthAuthenticationManager;
    }

}