package com.cloud.web.security;

import java.lang.reflect.Method;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.beans.factory.ListableBeanFactory;

/**
 * @author omidp
 *
 */
public class SecureAdvisor extends AbstractPointcutAdvisor {

	private final Pointcut pointcut;
	private final SecureInterceptor advice;

	public SecureAdvisor(ListableBeanFactory beanFactory, Class<SecureContext> scAnnotationType) {
		this.advice = new SecureInterceptor();
		this.pointcut = new StaticMethodMatcherPointcutAdvisor() {

			@Override
			public boolean matches(Method method, Class<?> targetClass) {
				boolean annotationPresent = method.isAnnotationPresent(scAnnotationType);
				if (annotationPresent) {
//					annotationPresent = isEnable(method.getAnnotation(SecureContext.class));
				}
				return annotationPresent;
			}
		};
	}

	@Override
	public Pointcut getPointcut() {
		return this.pointcut;
	}

	@Override
	public Advice getAdvice() {
		return advice;
	}

}