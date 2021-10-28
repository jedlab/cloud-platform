package com.cloud.userservice.app.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.jedlab.framework.spring.dao.PO;
import com.jedlab.framework.spring.security.SecurityUserContext;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sec_user")
@Getter
@Setter
public class UserEntity extends PO implements SecurityUserContext {

	@Column(name="user_name")
	private String username;
	
	@Column(name="user_password")
	private String password;
	
	@Column(name="is_enabled")
	@Type(type="yes_no")
	private boolean enabled;
	
	
	@ManyToMany(targetEntity = RoleEntity.class)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
//    @NotAudited
    private Set<RoleEntity> roles = new HashSet<>();
	

}
