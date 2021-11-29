package com.cloud.web.security;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.cloud.exceptions.NotAuthorizedException;
import com.cloud.usermanagement.SecurityUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecureInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation i) throws Throwable {
		Method method = i.getMethod();
		SecureContext sc = method.getAnnotation(SecureContext.class);
		String[] roles = sc.roles();
		if (roles != null && roles.length > 0) {
			for (int j = 0; j < roles.length; j++) {
				if (SecurityUtil.hasRole(roles[j])) {
					Object ret = i.proceed();
					return ret;
				}
			}
			throw new NotAuthorizedException("User doesn't have permission");
		}
		else
		{
			Object ret = i.proceed();
			return ret;
		}
	}

}
