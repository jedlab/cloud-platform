package com.cloud.oauthserver.app.config;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.cloud.oauthserver.app.CloudUserTokenConverter;

@EnableAuthorizationServer()
@Configuration()
public class AuthConfig extends AuthorizationServerConfigurerAdapter {

	private AuthenticationManager authenticationManager;
	private DataSource dataSource;
	private PasswordEncoder passwordEncoder;
	private UserDetailsService userDetailService;

	public AuthConfig(AuthenticationManager authenticationManager, DataSource dataSource,
			PasswordEncoder passwordEncoder, UserDetailsService userDetailService) throws Exception {
		this.authenticationManager = authenticationManager;
		this.dataSource = dataSource;
		this.passwordEncoder = passwordEncoder;
		this.userDetailService = userDetailService;
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()")
				.passwordEncoder(passwordEncoder);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// @formatter:off
        clients.jdbc(dataSource);
        // @formatter:on
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		// @formatter:off
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter()));
        endpoints.authenticationManager(this.authenticationManager)
        .pathMapping("/oauth/token", "/v1/token")
        .tokenStore(tokenStore())
        .accessTokenConverter(jwtAccessTokenConverter())
        .userDetailsService(userDetailService)
        .tokenEnhancer(tokenEnhancerChain)
        .tokenServices(defaultTokenServices())
        .reuseRefreshTokens(false)
        ;
        // @formatter:on
	}

	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
	}

	@Bean
	JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter tc = new JwtAccessTokenConverter();
		tc.setSigningKey("CLOUD!@#");
		tc.setAccessTokenConverter(accessTokenConverter());
		return tc;
	}

	@Bean
	DefaultTokenServices defaultTokenServices() {
		DefaultTokenServices dt = new DefaultTokenServices();
		dt.setAuthenticationManager(authenticationManager);
//		dt.setClientDetailsService(clientDetailsService);
		dt.setTokenStore(tokenStore());
		dt.setSupportRefreshToken(true);
		dt.setTokenEnhancer(jwtAccessTokenConverter());
		return dt;
	}
	
	@Bean 
	AccessTokenConverter accessTokenConverter()
	{
		DefaultAccessTokenConverter dtc = new DefaultAccessTokenConverter();
		dtc.setUserTokenConverter(new CloudUserTokenConverter());
		return dtc;
	}

	@Bean
	JdbcClientDetailsService jdbcClientDetailsService() {
		return new JdbcClientDetailsService(dataSource);
	}

}
