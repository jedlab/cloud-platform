package com.cloud.oauthserver.app.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.cloud.usermanagement.UserDetailsImpl;

public class CloudAuthenticationProvider extends DaoAuthenticationProvider { // implements AuthenticationProvider {

	protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
			UserDetails user) {
		UserDetailsImpl detail = (UserDetailsImpl)user;
		List<SimpleGrantedAuthority> roles = detail.getRoles().stream().map(m->new SimpleGrantedAuthority(m.getName())).collect(Collectors.toList());
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, user, roles);
		SecurityContextHolder.getContext().setAuthentication(token);
		return super.createSuccessAuthentication(principal, token, user);
	}

}
