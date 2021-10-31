package com.cloud.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jedlab.framework.spring.dao.PO;
import com.jedlab.framework.spring.security.SecurityUserContext;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sec_user")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Audited(targetAuditMode=RelationTargetAuditMode.NOT_AUDITED)
public class UserEntity extends AuditPO implements SecurityUserContext {

	@Column(name="user_name")
	@NotNull
	private String username;
	
	@Column(name="user_password")
	@NotNull
	private String password;
	
	@Column(name="is_enabled")
	@Type(type="yes_no")
	private boolean enabled;
	
	
	@ManyToMany(targetEntity = RoleEntity.class)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @NotAudited
    private Set<RoleEntity> roles = new HashSet<>();
	

}
