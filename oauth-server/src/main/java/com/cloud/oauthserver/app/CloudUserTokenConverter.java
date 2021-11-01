package com.cloud.oauthserver.app;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import com.cloud.usermanagement.UserDetailsImpl;

public class CloudUserTokenConverter extends DefaultUserAuthenticationConverter {

	@Override
	public Map<String, ?> convertUserAuthentication(Authentication authentication) {
		Map<String, Object> convertUserAuthentication = new LinkedHashMap<>();
		convertUserAuthentication.putAll(super.convertUserAuthentication(authentication));
		if (authentication != null) {
			Object details = authentication.getPrincipal();
			if (details instanceof UserDetailsImpl) {
				convertUserAuthentication.put("userId", ((UserDetailsImpl) details).getId());
			}
		}
		return convertUserAuthentication;
	}
}
