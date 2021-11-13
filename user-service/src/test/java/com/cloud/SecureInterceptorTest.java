package com.cloud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cloud.SecureInterceptorTest.SecureContextconfiguration;
import com.cloud.exceptions.NotAuthorizedException;
import com.cloud.web.security.SecureContext;
import com.cloud.web.security.SecurePostProcessor;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SecureContextconfiguration.class })
public class SecureInterceptorTest {

	
	@Autowired
	SecureBean sb;
	
	@Test()
	@Disabled
	public void test()
	{
		assertNotNull(sb);
		Exception exception = assertThrows(NotAuthorizedException.class, () ->
		sb.issecure());
		sb.notsecure();
		assertEquals("User doesn't have permission", exception.getMessage());
	}
	
	@Test
	public void testWithRole()
	{
		assertNotNull(sb);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("test","", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		sb.issecure();
		sb.notsecure();
	}
	
	
	
	public static class SecureBean
	{
		@SecureContext(roles = "ROLE_ADMIN")
		public void issecure()
		{
			
		}
		
		public void notsecure()
		{
			
		}
	}
	
	
	@Configuration
	public static class SecureContextconfiguration {

		@Bean
		public SecurePostProcessor trace() {
			return new SecurePostProcessor();
		}
		
		@Bean
		SecureBean seceureBean()
		{
			return new SecureBean();
		}

	}

}
