package com.cloud.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.cloud.exceptions.NotAuthorizedException;
import com.cloud.usermanagement.UserDetailsImpl;
import com.cloud.util.JsonUtil;
import com.cloud.web.proxy.CacheServiceProxy;
import com.jedlab.framework.util.StringUtil;

public class DefaultSecureContextImpl implements SecureContextLoader {

	CacheServiceProxy cacheServiceProxy;

	public DefaultSecureContextImpl(CacheServiceProxy cacheServiceProxy) {
		this.cacheServiceProxy = cacheServiceProxy;
	}

	@Override
	public void loadContext(HttpServletRequest request) {
		String token = request.getHeader("authorization");
		if (StringUtil.isEmpty(token))
			token = request.getHeader("Authorization");
		if(token == null)
			throw new NotAuthorizedException("Token not found");
		if (token.startsWith("bearer")) {
//			token = token.substring("bearer".length()).trim();
			Long userId = TokenUtil.extractUserIdFromJwtToken(token);
			String user = cacheServiceProxy.get(""+userId);
			if(user == null)
				throw new NotAuthorizedException("user not found in the cache");
			UserDetailsImpl userDetailsImpl = JsonUtil.toObject(user, UserDetailsImpl.class);
			if(userDetailsImpl == null)
				throw new NotAuthorizedException("user not found in the cache");
			UsernamePasswordAuthenticationToken upa = new UsernamePasswordAuthenticationToken(userDetailsImpl, "",
					userDetailsImpl.getRoles());
			SecurityContextHolder.getContext().setAuthentication(upa);
		}
	}

}
