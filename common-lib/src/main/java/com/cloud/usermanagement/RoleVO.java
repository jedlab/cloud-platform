package com.cloud.usermanagement;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RoleVO implements GrantedAuthority {

	private String name;

	@Override
	public String getAuthority() {
		return this.name;
	}

}
