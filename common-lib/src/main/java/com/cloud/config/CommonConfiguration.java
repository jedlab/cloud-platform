package com.cloud.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cloud.web.CommonExceptionHandler;
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

}
