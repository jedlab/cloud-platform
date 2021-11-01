package com.cloud.userservice.app.config;

import javax.sql.DataSource;

import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.domain.AclAuthorizationStrategyImpl;
import org.springframework.security.acls.domain.ConsoleAuditLogger;
import org.springframework.security.acls.domain.DefaultPermissionGrantingStrategy;
import org.springframework.security.acls.domain.SpringCacheBasedAclCache;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Configuration
public class AclConfig {

	@Bean
	public PermissionGrantingStrategy permissionGrantingStrategy() {
		return new DefaultPermissionGrantingStrategy(new ConsoleAuditLogger());
	}

	@Bean
	public AclAuthorizationStrategy aclAuthorizationStrategy() {
		return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	@Bean
	public SpringCacheBasedAclCache aclCache(CacheManager cm) {
		return new SpringCacheBasedAclCache(cm.getCache("aclCache"), 
				permissionGrantingStrategy(), aclAuthorizationStrategy());
	}
//
//	@Bean
//	public EhCacheFactoryBean aclEhCacheFactoryBean(EhCacheManagerFactoryBean cmf) {
//		EhCacheFactoryBean ehCacheFactoryBean = new EhCacheFactoryBean();
//		ehCacheFactoryBean.setCacheManager(cmf.getObject());
//		ehCacheFactoryBean.setCacheName("aclCache");
//		return ehCacheFactoryBean;
//	}

	@Bean
	public LookupStrategy lookupStrategy(DataSource dataSource, AclCache aclCache) {
		return new BasicLookupStrategy(dataSource, aclCache, aclAuthorizationStrategy(), new ConsoleAuditLogger());
	}
	
	@Bean
	public JdbcMutableAclService aclService(DataSource dataSource, LookupStrategy lookupStrategy, AclCache aclCache)
	{
		return new JdbcMutableAclService(dataSource, lookupStrategy, aclCache);
	}

}
