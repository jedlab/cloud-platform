package com.cloud.userservice.app.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.cloud.userservice.app.domain.MenuEntity;
import com.cloud.userservice.app.model.ExtendedBasePermission;
import com.jedlab.framework.spring.security.AuthenticationUtil;

@Service
public class PingService {

	@Autowired
	SecureAclService acls;
	
	public void testRoleAdmin()
	{
		System.out.println("this is test role admin");
		MenuEntity menuItem = new MenuEntity();
		menuItem.setId(1L);
		boolean hasPermission = acls.hasPermission(AuthenticationUtil.getAuthentication(), menuItem, Arrays.asList(ExtendedBasePermission.PRINT));
		System.out.println("hasPermission : "+hasPermission);
//		System.out.println("@@@@");
//		System.out.println(1 << 5);
//		System.out.println(1 << 6);
//		System.out.println(1 << 7);
//		
		if(hasPermission == false)
		{
//			acls.createAcl(menuItem.getId(), MenuEntity.class, new SimpleGrantedAuthority("ROLE_ADMIN"), ExtendedBasePermission.PRINT);
		}
//			acls.removeAcl(menuItem.getId(), MenuEntity.class);
//		acls.evitFromCache(1L);
	}
	
	public void testRoleUser()
	{
		System.out.println("this is test role user");
		
	}
	
	
}
