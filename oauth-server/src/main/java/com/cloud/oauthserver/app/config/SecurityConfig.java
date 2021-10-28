package com.cloud.oauthserver.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.cloud.oauthserver.app.service.UserDetailServiceLoaderImpl;
import com.cloud.usermanagement.UserDetailLoader;
import com.cloud.usermanagement.UserDetailServiceImpl;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

    private PasswordEncoder passwordEncoder;

    public SecurityConfig(PasswordEncoder passwordEncoder)
    {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http        
        .authorizeRequests()
            .antMatchers("/api/**").authenticated()
            .and()
        .httpBasic()
        ;
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService()
    {
    	UserDetailServiceImpl udt = new UserDetailServiceImpl();
    	udt.setUserDetailLoader(detailLoader());
        return udt;
    }
    
    
    @Bean 
    UserDetailLoader detailLoader()
    {
    	return new UserDetailServiceLoaderImpl();
    }
    
    
    
}