package com.cloud.userservice.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@ConfigurationProperties(prefix = "users")
@Data
@NoArgsConstructor
public class UserServiceConfiguration {

}
