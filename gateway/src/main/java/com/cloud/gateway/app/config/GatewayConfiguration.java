package com.cloud.gateway.app.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@ConfigurationProperties(prefix = "gateway")
@Data
@NoArgsConstructor
public class GatewayConfiguration {

	@Value("${gateway.clientId:sherkat55}")
	private String clientId;
	
	@Value("${gateway.clientSecret:sherkat55}")
	private String clientSecret;
	
	@Value("${gateway.resourceId:gateway}")
	private String resourceId;
	
}
