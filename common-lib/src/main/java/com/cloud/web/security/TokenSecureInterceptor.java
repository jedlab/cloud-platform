package com.cloud.web.security;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.cloud.exceptions.NotAuthorizedException;
import com.cloud.web.TokenUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenSecureInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation i) throws Throwable {
		Method method = i.getMethod();
		Object[] arguments = i.getArguments();
		if (arguments == null || arguments.length == 0)
			throw new NotAuthorizedException("First method argument must be token");
		String token = (String) arguments[0];
		var tokenRoles = TokenUtil.extractRoles(token);
		if(tokenRoles.isEmpty())
			throw new NotAuthorizedException("User doesn't have permission");
		TokenSecureContext sc = method.getAnnotation(TokenSecureContext.class);
		String[] roles = sc.roles();
		if (roles != null && roles.length > 0) {
			for (int j = 0; j < roles.length; j++) {
				if (tokenRoles.contains(roles[j])) {
					Object ret = i.proceed();
					return ret;
				}
			}
		}
		throw new NotAuthorizedException("User doesn't have permission");
	}

}
