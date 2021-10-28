package com.cloud.usermanagement;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailServiceImpl implements UserDetailsService {

	UserDetailLoader userDetailLoader;

	public void setUserDetailLoader(UserDetailLoader userDetailLoader) {
		this.userDetailLoader = userDetailLoader;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userDetailLoader.loadUserByUsername(username);
	}

}
