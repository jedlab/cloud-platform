package com.cloud.userservice.app.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
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
		boolean hasPermission = acls.hasPermission(AuthenticationUtil.getAuthentication(), menuItem, Arrays.asList(ExtendedBasePermission.SCAN));
		System.out.println(hasPermission);
		System.out.println("@@@@");
		System.out.println(1 << 5);
		System.out.println(1 << 6);
		System.out.println(1 << 7);
		acls.evitFromCache(1L);
	}
	
	public void testRoleUser()
	{
		System.out.println("this is test role user");
		
	}
	
	
}
