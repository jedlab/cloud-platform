package com.cloud.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.cloud.web.proxy.CacheServiceProxy;

public class SecureContextHandlerAdapter implements HandlerAdapter {

	SecureContextLoader secureContextLoader;
	RequestMappingHandlerAdapter rmh;

	public SecureContextHandlerAdapter(CacheServiceProxy cacheServiceProxy, RequestMappingHandlerAdapter rmh) {
		this.secureContextLoader = new DefaultSecureContextImpl(cacheServiceProxy);
		this.rmh = rmh;
	}

	@Override
	public boolean supports(Object handler) {
		return (handler instanceof HandlerMethod);
	}

	@Override
	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		try {
			HandlerMethod hm = (HandlerMethod) handler;
			SecureContext methodAnnotation = hm.getMethodAnnotation(SecureContext.class);
			if (methodAnnotation == null)
				methodAnnotation = hm.getBeanType().getAnnotation(SecureContext.class);
			if (methodAnnotation != null) {
				secureContextLoader.loadContext(request, methodAnnotation);
			}
			ModelAndView handle = rmh.handle(request, response, handler);
			return handle;
		} finally {
			SecurityContextHolder.clearContext();
		}
	}

	@Override
	public long getLastModified(HttpServletRequest request, Object handler) {
		return -1;
	}

}