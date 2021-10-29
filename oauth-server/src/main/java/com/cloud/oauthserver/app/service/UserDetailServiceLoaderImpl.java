package com.cloud.oauthserver.app.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cloud.usermanagement.RoleVO;
import com.cloud.usermanagement.UserDetailLoader;
import com.cloud.usermanagement.UserDetailsImpl;

public class UserDetailServiceLoaderImpl implements UserDetailLoader {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@SuppressWarnings("deprecation")
	@Override
	public UserDetailsImpl loadUserByUsername(String username) {
		try {
			UserDetailsImpl result = jdbcTemplate.queryForObject(
					"select id, user_name, user_password, is_enabled from sec_user where user_name = ?",
					new Object[] { username }, (rs, rowNum) -> {
						UserDetailsImpl vo = new UserDetailsImpl();
						vo.setUsername(rs.getString("user_name"));
						vo.setPassword(rs.getString("user_password"));
						vo.setEnabled("Y".equals(rs.getString("is_enabled")));
						vo.setId(rs.getLong("id"));
						return vo;
					});
			//
			var roleQuery = """
					select name from sec_role where id in (
					  select role_id from users_roles where user_id = (select id from sec_user where user_name = ?)
					)
					""";
			List<RoleVO> roles = jdbcTemplate.query(roleQuery, new Object[] {username}, (RowMapper<RoleVO>) (rs, rowNum) -> new RoleVO(rs.getString("name")));
			result.setRoles(new HashSet<>(roles));
			return result;
		} catch (IncorrectResultSizeDataAccessException nre) {

		}
		throw new UsernameNotFoundException("invalid username or password");
	}

}
