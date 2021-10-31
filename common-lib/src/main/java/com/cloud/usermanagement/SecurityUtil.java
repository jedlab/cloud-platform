package com.cloud.usermanagement;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.jedlab.framework.spring.security.AuthenticationUtil;

public class SecurityUtil {

	private SecurityUtil() {

	}

	public static Collection<? extends GrantedAuthority> getUserRoles() {
		if (AuthenticationUtil.isLoggedIn()) {
			Authentication auth = AuthenticationUtil.getAuthentication();
			Object principal = auth.getPrincipal();
			if (principal instanceof String || principal.equals("anonymousUser"))
				return null;
			if (principal instanceof UserDetailsImpl) {
				UserDetailsImpl applicationUser = ((UserDetailsImpl) principal);
				Collection<? extends GrantedAuthority> authorities = applicationUser.getAuthorities();
				return authorities;
			}

		}
		return null;
	}

	public static boolean hasRole(String roleName) {
		boolean hasRole = false;
		Collection<? extends GrantedAuthority> userRoles = getUserRoles();
		for (GrantedAuthority grantedAuthority : userRoles) {
			if (roleName.equals(grantedAuthority.getAuthority())) {
				return true;
			}
		}
		return hasRole;
	}

}
