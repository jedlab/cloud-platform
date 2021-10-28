package com.cloud.usermanagement;

public interface UserDetailLoader {
	UserDetailsImpl loadUserByUsername(String username);
}