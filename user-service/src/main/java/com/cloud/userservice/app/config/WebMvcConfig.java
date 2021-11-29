package com.cloud.userservice.app.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cloud.web.FileLoaderServlet;
import com.cloud.web.LoadFileProperties;
import com.cloud.web.proxy.CacheServiceProxy;
import com.cloud.web.security.SecurityContextArgumentResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	CacheServiceProxy userCacheServiceProxy;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new SecurityContextArgumentResolver(userCacheServiceProxy));
		WebMvcConfigurer.super.addArgumentResolvers(resolvers);
	}

}