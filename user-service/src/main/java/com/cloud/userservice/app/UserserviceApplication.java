package com.cloud.userservice.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.cloud.config.CommonConfiguration;
import com.cloud.config.acl.EnableACL;
import com.cloud.userservice.app.config.UserServiceConfiguration;
import com.cloud.web.proxy.CacheServiceProxy;
import com.cloud.web.security.SecureContextHandlerAdapter;

@SpringBootApplication
@Import(value = { CommonConfiguration.class })
@EnableConfigurationProperties(value = { UserServiceConfiguration.class })
@EnableFeignClients(basePackageClasses = CacheServiceProxy.class)
@EnableACL
public class UserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}

}
