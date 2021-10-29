package com.cloud.usermanagement;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.omidbiz.core.axon.internal.IgnoreElement;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jedlab.framework.spring.security.SecurityUserContext;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserDetailsImpl implements UserDetails, SecurityUserContext{

	private Long id;
	
	private Set<RoleVO> roles = new HashSet<>();
	
	private boolean enabled;

    private String username;

    private String password;
	
	@Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }
    
    
    @Override
    @IgnoreElement
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return getRoles();
    }


}
