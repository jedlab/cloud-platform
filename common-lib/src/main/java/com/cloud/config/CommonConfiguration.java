package com.cloud.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.DelegatingMessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.cloud.web.CommonExceptionHandler;
import com.cloud.web.proxy.CacheServiceProxy;
import com.cloud.web.security.SecureContextHandlerAdapter;
import com.jedlab.framework.spring.SpringUtil;

@Configuration
public class CommonConfiguration {

	@Bean
	SpringUtil springUtil() {
		return new SpringUtil();
	}

	@Bean
	CommonExceptionHandler commonExceptionHandler(MessageSource msgSource) {
		return new CommonExceptionHandler(msgSource);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(4);
	}

	@Bean
	MessageSource messageSource() {
		DelegatingMessageSource dms = new DelegatingMessageSource();
		ReloadableResourceBundleMessageSource rms = new ReloadableResourceBundleMessageSource();
		rms.setDefaultLocale(new Locale("fa", "IR"));
		rms.setUseCodeAsDefaultMessage(true);
		dms.setParentMessageSource(rms);
		return dms;
	}

	@Bean
	SecureContextHandlerAdapter secureContextHandlerAdapter(CacheServiceProxy cacheProxy,
			RequestMappingHandlerAdapter emh) {
		return new SecureContextHandlerAdapter(cacheProxy, emh);
	}

}
