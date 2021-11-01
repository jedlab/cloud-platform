package com.cloud.userservice.app.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.stereotype.Service;

import com.baeldung.acl.persistence.entity.NoticeMessage;
import com.jedlab.framework.spring.security.AuthenticationUtil;

@Service
public class PingService {

	@Autowired
	SecureAclService acls;
	
	public void testRoleAdmin()
	{
		System.out.println("this is test role admin");
		NoticeMessage notification = new NoticeMessage(1L);
		boolean hasPermission = acls.hasPermission(AuthenticationUtil.getAuthentication(), notification, Arrays.asList(BasePermission.DELETE));
		System.out.println(hasPermission);
		
	}
	
	public void testRoleUser()
	{
		System.out.println("this is test role user");
		
	}
	
	
}
