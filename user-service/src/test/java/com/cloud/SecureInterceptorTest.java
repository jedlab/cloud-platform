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
import com.cloud.web.security.TokenSecureContext;
import com.cloud.web.security.TokenSecurePostProcessor;

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
		sb.issecure("test"));
		sb.notsecure();
		assertEquals("User doesn't have permission", exception.getMessage());
	}
	
	@Test
	public void testWithRole()
	{
		assertNotNull(sb);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("test","", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		sb.issecure("bearer test");
		sb.notsecure();
		sb.secureToken("bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiZ2F0ZXdheSJdLCJ1c2VyX25hbWUiOiJhZG1pbmFkbWluIiwic2NvcGUiOlsiUkVBRCIsIldSSVRFIl0sImV4cCI6MTYzNjgzNjA3NSwidXNlcklkIjoyMCwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiJdLCJqdGkiOiJGMmlqdlNVeHo3SzNBM1ppTGNreF9HVXNod0kiLCJjbGllbnRfaWQiOiJzaGVya2F0NTUifQ.pHkIbi8axSSK7z-aJ4FmMDvHR4y0cj8yPMuUI11Y0z4");
	}
	
	
	
	public static class SecureBean
	{
		@SecureContext(roles = "ROLE_ADMIN")
		public void issecure(String token)
		{
			
		}
		
		public void notsecure()
		{
			
		}
		
		@TokenSecureContext(roles = "ROLE_ADMIN")
		public void secureToken(String token)
		{
			
		}
		
	}
	
	
	@Configuration
	public static class SecureContextconfiguration {

		@Bean
		public SecurePostProcessor securePostProcessor() {
			return new SecurePostProcessor();
		}
		
		@Bean
		public TokenSecurePostProcessor tokenSecurePostProcessor() {
			return new TokenSecurePostProcessor();
		}
		
		@Bean
		SecureBean seceureBean()
		{
			return new SecureBean();
		}

	}

}
