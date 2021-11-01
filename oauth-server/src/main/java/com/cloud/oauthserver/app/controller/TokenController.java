package com.cloud.oauthserver.app.controller;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.model.KeyValue;
import com.cloud.usermanagement.GrantType;
import com.cloud.usermanagement.UserDetailsImpl;
import com.cloud.util.JsonUtil;
import com.cloud.web.proxy.CacheServiceProxy;
import com.jedlab.framework.exceptions.ServiceException;
import com.jedlab.framework.spring.rest.ResponseMessage;
import com.jedlab.framework.spring.security.AuthenticationUtil;
import com.jedlab.framework.util.CollectionUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TokenController {

	@Autowired
	TokenEndpoint tokenEndPoint;

	@Autowired
	CacheServiceProxy cacheServiceProxy;

	@Autowired
	ResourceServerTokenServices tokenService;

	@Autowired
	private AccessTokenConverter accessTokenConverter;
	
	@Autowired
	TokenStore tokenStore;

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
				cacheServiceProxy.put(new KeyValue(""+securityUser.getId(), JsonUtil.toJson(securityUser)));
			}
			return ResponseEntity.ok(body);
		} catch (HttpRequestMethodNotSupportedException e) {
			throw new ServiceException(e.getMessage());
		} finally {
			SecurityContextHolder.clearContext();
		}
	}

	@RequestMapping(value = { "/v1/checkToken" }, method = { RequestMethod.POST })
	public ResponseEntity<Map<String, ?>> checkToken(@RequestBody String input) {
		String val = "" + JsonUtil.getJsonFieldValue(input, "token");
		if (val.startsWith("bearer "))
			val = val.substring("bearer ".length());
		OAuth2AccessToken token = tokenService.readAccessToken(val);
		if (token == null) {
			Map<String, String> error = new HashMap<>();
			error.put("error", "Token was not recognised");
			log.warn("403 Exception : {}", error.toString());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
		}

		if (token.isExpired()) {
			Map<String, String> error = new HashMap<>();
			error.put("error", "Token is expired");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
		}

		OAuth2Authentication authentication = tokenService.loadAuthentication(token.getValue());

		Map<String, Object> response = (Map<String, Object>) accessTokenConverter.convertAccessToken(token,
				authentication);

		// gh-1070
		response.put("active", true);

		return ResponseEntity.ok(response);

	}
	
	
	@PostMapping(value = { "/v1/removeTokenByClientAndUsername" })
    public ResponseEntity<ResponseMessage> removeTokenByClientAndUsername(@RequestBody String tokenJson)
    {
        log.info("removeTokenByClientAndUsername");
        JSONObject json = new JSONObject(tokenJson);
        if (json.has("client") == false || json.has("username") == false)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("fail", 100));        
        Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientIdAndUserName(json.getString("client"), json.getString("username"));
        if (CollectionUtil.isNotEmpty(tokens))
        {
            tokens.forEach(t -> {
                tokenStore.removeRefreshToken(t.getRefreshToken());
                tokenStore.removeAccessToken(t);
            });
        }
        log.info("token removed");
        return ResponseEntity.ok(new ResponseMessage("succes", 0));

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
