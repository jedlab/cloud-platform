package com.cloud.config.acl;

import javax.sql.DataSource;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.domain.AclAuthorizationStrategyImpl;
import org.springframework.security.acls.domain.ConsoleAuditLogger;
import org.springframework.security.acls.domain.DefaultPermissionFactory;
import org.springframework.security.acls.domain.DefaultPermissionGrantingStrategy;
import org.springframework.security.acls.domain.SpringCacheBasedAclCache;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.cloud.web.security.ExtendedBasePermission;


@Configuration
public class AclConfiguration {

	@Bean
	public PermissionGrantingStrategy permissionGrantingStrategy() {
		return new DefaultPermissionGrantingStrategy(new ConsoleAuditLogger());
	}

	@Bean
	public AclAuthorizationStrategy aclAuthorizationStrategy() {
		return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_HYPER_ADMIN"));
	}

	@Bean
	public SpringCacheBasedAclCache aclCache(CacheManager cm) {
		return new SpringCacheBasedAclCache(cm.getCache("aclCache"), permissionGrantingStrategy(),
				aclAuthorizationStrategy());
	}


	@Bean
	public LookupStrategy lookupStrategy(DataSource dataSource, AclCache aclCache) {
		BasicLookupStrategy basicLookupStrategy = new BasicLookupStrategy(dataSource, aclCache,
				aclAuthorizationStrategy(), new ConsoleAuditLogger());
		basicLookupStrategy.setPermissionFactory(new DefaultPermissionFactory(ExtendedBasePermission.class));
		return basicLookupStrategy;
	}

	@Bean
	public JdbcMutableAclService aclService(DataSource dataSource, LookupStrategy lookupStrategy, AclCache aclCache) {
		return new JdbcMutableAclService(dataSource, lookupStrategy, aclCache);
	}
	
	@Bean 
	SecureAclService secureAclService(MutableAclService aclService, AclCache aclCache)
	{
		return new SecureAclService(aclService, aclCache);
	}
	
	
}
