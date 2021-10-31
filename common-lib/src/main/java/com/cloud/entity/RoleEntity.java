package com.cloud.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.jedlab.framework.spring.dao.BasePO;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sec_role")
@Getter
@Setter
public class RoleEntity extends BasePO implements GrantedAuthority {

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Override
	public String getAuthority() {
		return getName();
	}

}
