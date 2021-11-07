package com.cloud.eureka.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
@EnableConfigServer
public class EurekaApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(EurekaApplication.class, args);
	}

}
