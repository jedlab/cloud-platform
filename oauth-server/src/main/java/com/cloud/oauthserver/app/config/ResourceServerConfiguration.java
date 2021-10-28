package com.cloud.oauthserver.app.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "resource-id";
    private static final String SECURED_READ_SCOPE = "#oauth2.hasScope('message:read')";
    private static final String SECURED_WRITE_SCOPE = "#oauth2.hasScope('message:write')";
    private static final String SECURED_PATTERN = "/api/**";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http        
        .authorizeRequests()
            .antMatchers("/api/**").authenticated()
            .and()
        .httpBasic()
                ;
    }
}
