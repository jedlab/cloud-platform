package com.cloud.usermanagement;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown=true)
@ToString
public class RoleVO implements GrantedAuthority {

	private String name;

	@Override
	public String getAuthority() {
		return this.name;
	}

}
