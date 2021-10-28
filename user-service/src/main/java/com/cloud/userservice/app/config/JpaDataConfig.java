package com.cloud.userservice.app.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cloud.userservice.app.dao.UserDao;
import com.cloud.userservice.app.domain.UserEntity;

/**
 *
 * @author Omid Pourhadi
 *
 */
@Configuration
@EntityScan(basePackageClasses = { UserEntity.class })
@EnableJpaRepositories(basePackageClasses = { UserDao.class })
@EnableJpaAuditing
public class JpaDataConfig {

//    @Bean
//    public AuditorAware<User> auditAware()
//    {
//        return new SpringSecurityAuditorAware();
//    }

}