package com.cloud.oauthserver.app.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@RestController
@RequestMapping("/clients")
public class ClientController {

	@Autowired
	JdbcClientDetailsService clientDetailsService;
	
	@Autowired
	PasswordEncoder encoder;
	
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody ClientDetailVO vo)
	{
		String secret = encoder.encode(vo.getPass());
		DefaultClientDetails defaultClientDetails = new DefaultClientDetails(vo.getClientId(), vo.getPass(), 
				vo.getScopes(), vo.getRoles(), vo.getAdditionalInformation(), secret);
		clientDetailsService.addClientDetails(defaultClientDetails);
		return ResponseEntity.ok(defaultClientDetails);
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ClientDetailVO implements Serializable
	{
		private String clientId;
		private String pass;
		private Set<String> scopes;
		private Collection<String> roles;
		private Map<String, Object> additionalInformation;
	}
	
	public static class DefaultClientDetails implements ClientDetails
	{

		private String clientId;
		private String plainPassword;
		private Set<String> scopes;
		private Collection<String> roles;
		private Map<String, Object> additionalInformation;
		private String encodedPassword;
		
		
		
		public DefaultClientDetails(String clientId, String pass, Set<String> scopes, Collection<String> roles,
				Map<String, Object> additionalInformation, String encodedPassword) {
			this.clientId = clientId;
			this.plainPassword = pass;
			this.scopes = scopes;
			this.roles = roles;
			this.additionalInformation = additionalInformation;
			this.encodedPassword = encodedPassword;
		}

		@Override
		public String getClientId() {
			return clientId;
		}

		@Override
		public Set<String> getResourceIds() {
			return new HashSet<>(Arrays.asList("gateway"));
		}

		@Override
		public boolean isSecretRequired() {
			return true;
		}

		@Override
		public String getClientSecret() {
			return encodedPassword;
		}

		@Override
		public boolean isScoped() {
			return true;
		}

		@Override
		public Set<String> getScope() {
			return scopes;
		}

		@Override
		public Set<String> getAuthorizedGrantTypes() {
			return new HashSet<>(Arrays.asList("password", "refresh_token", "client_credentials"));
		}

		@Override
		public Set<String> getRegisteredRedirectUri() {
			return null;
		}

		@Override
		public Collection<GrantedAuthority> getAuthorities() {
			return roles.stream().map(m->new SimpleGrantedAuthority(m)).collect(Collectors.toList());
		}

		@Override
		public Integer getAccessTokenValiditySeconds() {
			return 60_000_000;
		}

		@Override
		public Integer getRefreshTokenValiditySeconds() {
			return 60_000_000;
		}

		@Override
		public boolean isAutoApprove(String scope) {
			return false;
		}

		@Override
		public Map<String, Object> getAdditionalInformation() {
			return additionalInformation;
		}
		
		public String getAuthHeaderPass()
		{
			String str= getClientId()+":"+plainPassword;
			return "Basic " + Base64.getUrlEncoder().encodeToString(str.getBytes());
		}
		
	}
	
}
