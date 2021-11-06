package com.cloud.gateway.app.config;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class CloudRemoteTokenServices implements ResourceServerTokenServices {

//	private RestOperations restTemplate;

	private String clientId;

	private String clientSecret;

	private WebClient webClient;

	private AccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();

	public CloudRemoteTokenServices(Builder builder) {
		this.webClient = builder.build();
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	@Override
	public OAuth2Authentication loadAuthentication(String accessToken)
			throws AuthenticationException, InvalidTokenException {
		JSONObject input = new JSONObject();
		input.put("token", accessToken);
		String body = input.toString();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", getAuthorizationHeader(clientId, clientSecret));

		Mono<String> response = webClient.post().uri("http://AUTH-SERVER/v1/checkToken")
				.accept(MediaType.APPLICATION_JSON_UTF8).bodyValue(body).retrieve().bodyToMono(String.class);

		String tokenResponse = response.block();
		log.info("check token endpoint response {}", tokenResponse);
		Map<String, Object> map = new JSONObject(tokenResponse).toMap();
		// gh-838
		if (map.containsKey("active") && !"true".equals(String.valueOf(map.get("active")))) {
			throw new InvalidTokenException(accessToken);
		}
		return tokenConverter.extractAuthentication(map);
	}

	@Override
	public OAuth2AccessToken readAccessToken(String accessToken) {
		throw new UnsupportedOperationException("Not supported: read access token");
	}

	private String getAuthorizationHeader(String clientId, String clientSecret) {

		if (clientId == null || clientSecret == null) {
			log.info("Null Client ID or Client Secret detected. Endpoint that requires authentication will reject request with 401 error.");
		}

		String creds = String.format("%s:%s", clientId, clientSecret);
		try {
			return "Basic " + new String(Base64.encode(creds.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("Could not convert String");
		}
	}

}
