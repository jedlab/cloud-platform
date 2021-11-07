package com.cloud.oauthserver.app.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cloud.oauthserver.app.service.UserDetailServiceLoaderImpl;
import com.cloud.usermanagement.UserDetailLoader;
import com.cloud.usermanagement.UserDetailServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private PasswordEncoder passwordEncoder;

	public SecurityConfig(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		AuthenticationManager am = new ProviderManager(Arrays.asList(authenticationProvider()));
		return am;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().formLogin().disable()
		.authenticationProvider(authenticationProvider())
		.authorizeRequests()
		.antMatchers(HttpMethod.OPTIONS, "**").permitAll()
				.antMatchers("/api/**").authenticated();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.eraseCredentials(true).userDetailsService(userDetailsService()).and()
				.authenticationProvider(authenticationProvider());
	}

	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		UserDetailServiceImpl udt = new UserDetailServiceImpl();
		udt.setUserDetailLoader(detailLoader());
		return udt;
	}

	@Bean
	UserDetailLoader detailLoader() {
		return new UserDetailServiceLoaderImpl();
	}

	@Bean
	AuthenticationProvider authenticationProvider() {
		CloudAuthenticationProvider daop = new CloudAuthenticationProvider();
		daop.setUserDetailsService(userDetailsService());
		daop.setPasswordEncoder(passwordEncoder);
		return daop;
	}

	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return userDetailsService();
	}

}