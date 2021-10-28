package com.cloud.oauthserver.app;

import javax.sql.DataSource;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import com.cloud.config.CommonConfiguration;

@SpringBootApplication
@Import(value = {CommonConfiguration.class})
public class OAuthServerApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(OAuthServerApplication.class, args);
    }

    @Bean
    ApplicationRunner app(ApplicationContext ctx)
    {
        return args -> {
            String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
            for (int i = 0; i < beanDefinitionNames.length; i++)
            {
                System.out.println(beanDefinitionNames[i]);
            }
        };
    }
    
    @Bean
    JdbcTemplate jdbcTemplate(DataSource datasource)
    {
    	return new JdbcTemplate(datasource);
    }

}
