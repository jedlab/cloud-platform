package com.cloud.userservice.app.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.userservice.app.service.PingService;
import com.cloud.web.SecureContext;
import com.jedlab.framework.spring.security.AuthenticationUtil;

@RestController
@RequestMapping("/ping")
//@SecureContext
public class PingController {
	
	@Autowired
	PingService ps;
	
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
	@SecureContext
	public ResponseEntity<String> ping(@RequestBody String inp)
	{
		System.out.println(AuthenticationUtil.getUserId());
		System.out.println(AuthenticationUtil.getUsername());
		Collection<? extends GrantedAuthority> authorities = AuthenticationUtil.getAuthentication().getAuthorities();
		authorities.forEach(System.out::println);
		ps.testRoleAdmin();
		ps.testRoleUser();
		return ResponseEntity.ok("pong");
	}
	
	
	@RequestMapping(value="/test",method = {RequestMethod.GET})
	public ResponseEntity<String> test()
	{
		return ResponseEntity.ok("test");
	}

}
