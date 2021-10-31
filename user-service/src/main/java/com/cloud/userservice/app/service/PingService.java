package com.cloud.userservice.app.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class PingService {

	public void testRoleAdmin()
	{
		System.out.println("this is test role admin");
		
	}
	
	public void testRoleUser()
	{
		System.out.println("this is test role user");
		
	}
	
	
}
