package com.cloud.oauthserver.app;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

public class CloudUserTokenConverter extends DefaultUserAuthenticationConverter {

	@Override
	public Map<String, ?> convertUserAuthentication(Authentication authentication) {
		return super.convertUserAuthentication(authentication);
	}
}
