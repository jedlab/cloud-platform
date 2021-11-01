package com.cloud.userservice.app.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.cloud.entity.UserEntity;
import com.cloud.userservice.app.dao.MenuDao;
import com.cloud.userservice.app.dao.UserDao;
import com.cloud.userservice.app.domain.MenuEntity;

/**
 *
 * @author Omid Pourhadi
 *
 */
@Configuration
@EntityScan(basePackageClasses = { UserEntity.class , MenuEntity.class})
@EnableJpaRepositories(basePackageClasses = { UserDao.class, MenuDao.class })
@EnableJpaAuditing(auditorAwareRef = "auditAware")
@EnableTransactionManagement
public class JpaDataConfig {

    @Bean
    public AuditorAware<Long> auditAware()
    {
        return new SpringSecurityAuditorAware();
    }

}