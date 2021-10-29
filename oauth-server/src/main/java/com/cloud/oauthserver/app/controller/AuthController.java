package com.cloud.oauthserver.app.controller;

import java.io.Serializable;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.model.KeyValue;
import com.cloud.usermanagement.GrantType;
import com.cloud.usermanagement.UserDetailsImpl;
import com.cloud.util.JsonUtil;
import com.cloud.web.proxy.CacheServiceProxy;
import com.jedlab.framework.exceptions.ServiceException;
import com.jedlab.framework.spring.security.AuthenticationUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AuthController {

	@Autowired
	TokenEndpoint tokenEndPoint;

	@Autowired
	CacheServiceProxy cacheServiceProxy;

	@PostMapping("/v1/token")
	public ResponseEntity<?> getToken(Principal principal, @RequestBody TokenRequest tr) {
		try {
			Map<String, String> details = new HashMap<>();
			details.put("username", tr.getUsername());
			details.put("password", tr.getPassword());
			details.put("grant_type", GrantType.findByValue(tr.getGrantType()).name().toLowerCase());
			ResponseEntity<OAuth2AccessToken> postAccessToken = tokenEndPoint.postAccessToken(principal, details);
			OAuth2AccessToken body = postAccessToken.getBody();
			if (postAccessToken.getStatusCodeValue() == 200) {
				UserDetailsImpl securityUser = (UserDetailsImpl) AuthenticationUtil.getSecurityUser();
				securityUser.setPassword("");
				cacheServiceProxy.put(new KeyValue(body.getValue(), JsonUtil.toJson(securityUser)));
			}
			return ResponseEntity.ok(body);
		} catch (HttpRequestMethodNotSupportedException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter
	@ToString
	public static class TokenRequest implements Serializable {
		private String grantType;
		private String username;
		private String password;
	}

}
