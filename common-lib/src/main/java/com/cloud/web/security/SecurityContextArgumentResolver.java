package com.cloud.web.security;

import java.util.Iterator;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.cloud.exceptions.NotAuthorizedException;
import com.cloud.usermanagement.UserDetailsImpl;
import com.cloud.util.JsonUtil;
import com.cloud.web.TokenUtil;
import com.cloud.web.proxy.CacheServiceProxy;
import com.jedlab.framework.exceptions.ServiceException;

/**
 * @author omidp
 *
 */
public class SecurityContextArgumentResolver implements HandlerMethodArgumentResolver {

	CacheServiceProxy cacheServiceProxy;

	public SecurityContextArgumentResolver(CacheServiceProxy cacheServiceProxy) {
		this.cacheServiceProxy = cacheServiceProxy;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return UserDetailsImpl.class.equals(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		String authorizationHeader = getAuthorizationHeader(webRequest);
		Long userId = TokenUtil.extractUserIdFromJwtToken(authorizationHeader);
		if (userId == null)
			throw new ServiceException("Invalid authorization header user Id not found");
		String user = cacheServiceProxy.get("" + userId);
		if (user == null)
			throw new NotAuthorizedException("user not found in the cache");
		UserDetailsImpl userDetailsImpl = JsonUtil.toObject(user, UserDetailsImpl.class);
		if (userDetailsImpl == null)
			throw new NotAuthorizedException("UserDetailsImpl not found in the cache");
		return userDetailsImpl;
	}

	private String getAuthorizationHeader(NativeWebRequest webRequest) {
		Iterator<String> headerNames = webRequest.getHeaderNames();
		while (headerNames.hasNext()) {
			String name = (String) headerNames.next();
			if ("X-Authorization".equals(name)) {
				return webRequest.getHeader(name);
			}
			if ("authorization".equals(name) || "Authorization".equals(name)) {
				return webRequest.getHeader(name);
			}
		}
		return null;
	}

}
