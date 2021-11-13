package com.cloud.web;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jedlab.framework.exceptions.ServiceException;
import com.jedlab.framework.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenUtil {

	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	private TokenUtil() {

	}

	public static Long extractUserIdFromJwtToken(String authorization) {
		Long userId = null;
		try {
			if (authorization != null && authorization.startsWith("bearer ")) {
				String substring = getJwtToken(authorization);
				Jwt jwt = JwtHelper.decode(substring);
				String claimsStr = jwt.getClaims();
				Map<String, Object> claims = (Map<String, Object>) MAPPER.readValue(claimsStr, Object.class);
				if (claims == null)
					return null;
				Object claimUserId = claims.get("userId");
				if (claimUserId == null)
					return null;
				String uid = String.valueOf(claimUserId);
				if (StringUtil.isNotEmpty(uid))
					userId = Long.parseLong(uid);
			}
		} catch (Exception e) {
			log.info("{}", e);
		}
		return userId;
	}
	
	public static Set<String> extractRoles(String authorization) {
		Set<String> roles = new HashSet<>();
		try {
			if (authorization != null && authorization.startsWith("bearer ")) {
				String substring = getJwtToken(authorization);
				Jwt jwt = JwtHelper.decode(substring);
				String claimsStr = jwt.getClaims();
				Map<String, Object> claims = (Map<String, Object>) MAPPER.readValue(claimsStr, Object.class);
				if (claims == null)
					return null;
				Object claimScope = claims.get("authorities");
				if (claimScope == null)
					return null;
				return new HashSet<>((Collection) claimScope);
			}
		} catch (Exception e) {
			log.info("{}", e);
		}
		return roles;
	}
	
	public static Set<String> extractScopes(String authorization) {
		Set<String> roles = new HashSet<>();
		try {
			if (authorization != null && authorization.startsWith("bearer ")) {
				String substring = getJwtToken(authorization);
				Jwt jwt = JwtHelper.decode(substring);
				String claimsStr = jwt.getClaims();
				Map<String, Object> claims = (Map<String, Object>) MAPPER.readValue(claimsStr, Object.class);
				if (claims == null)
					return null;
				Object claimScope = claims.get("scope");
				if (claimScope == null)
					return null;
				return new HashSet<>((Collection) claimScope);
			}
		} catch (Exception e) {
			log.info("{}", e);
		}
		return roles;
	}

	public static String getJwtToken(String authorization) {
		if (StringUtil.isEmpty(authorization)) {
			throw new ServiceException("Authorization header cannot be empty ...");
		}
		if (authorization.startsWith("bearer ") == false)
			throw new ServiceException("Authorization header should start with bearer ");
		return authorization.substring("bearer ".length()).trim();
	}

}
