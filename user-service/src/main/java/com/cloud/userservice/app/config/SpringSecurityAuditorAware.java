package com.cloud.userservice.app.config;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.cloud.entity.UserEntity;
import com.cloud.usermanagement.UserDetailsImpl;
import com.jedlab.framework.spring.security.AuthenticationUtil;

/**
 *
 * @author Omid Pourhadi
 *
 */
public class SpringSecurityAuditorAware implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
//		return Optional.of(1L);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Optional<String> op = Optional.empty();
		if (AuthenticationUtil.isLoggedIn() == false)
			return op;
		if (authentication == null || !authentication.isAuthenticated()) {
			return op;
		}
		Object principal = authentication.getPrincipal();
		if (principal instanceof String) {
			if (principal.equals("anonymousUser") || principal.equals("cloud"))
				return op;
		}
		if (principal instanceof UserDetailsImpl) {
			var userDetailsImpl = (UserDetailsImpl) principal;					
			return Optional.of(userDetailsImpl.getUsername());
		}
		return Optional.empty();
	}

}