package com.cloud.userservice.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cloud.config.CommonConfiguration;
import com.cloud.userservice.app.config.UserServiceConfiguration;

@SpringBootApplication
@Import(value = {CommonConfiguration.class})
@EnableConfigurationProperties(value = {UserServiceConfiguration.class})
public class UserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}
	
	
	

}
