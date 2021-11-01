package com.cloud.web;

import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.cloud.exceptions.NotAuthorizedException;
import com.cloud.usermanagement.UserDetailsImpl;
import com.cloud.util.JsonUtil;
import com.cloud.web.proxy.CacheServiceProxy;
import com.jedlab.framework.util.CollectionUtil;
import com.jedlab.framework.util.StringUtil;

public class DefaultSecureContextImpl implements SecureContextLoader {

	CacheServiceProxy cacheServiceProxy;

	public DefaultSecureContextImpl(CacheServiceProxy cacheServiceProxy) {
		this.cacheServiceProxy = cacheServiceProxy;
	}

	@Override
	public void loadContext(HttpServletRequest request, SecureContext sc) {
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
			String[] roles = sc.roles();
			if(roles != null && roles.length > 0)
			{
				Stream.of(roles).forEach(r->checkUseHasRole(r, userDetailsImpl));
			}
			UsernamePasswordAuthenticationToken upa = new UsernamePasswordAuthenticationToken(userDetailsImpl, "",
					userDetailsImpl.getRoles());
			SecurityContextHolder.getContext().setAuthentication(upa);
		}
	}

	private void checkUseHasRole(String role, UserDetailsImpl userDetailsImpl) {
		long count = userDetailsImpl.getAuthorities().stream().filter(f->f.getAuthority().equals(role)).count();
		if(count == 0)
			throw new NotAuthorizedException("Token does not have role : " + role);
	}

}
