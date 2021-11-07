package com.cloud.userservice.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.entity.UserEntity;
import com.cloud.userservice.app.model.UserResponse;
import com.cloud.userservice.app.service.UserService;
import com.jedlab.framework.spring.rest.AbstractQueryRestController;
import com.jedlab.framework.spring.rest.EntityModelMapper;
import com.jedlab.framework.spring.rest.QueryWhereParser.FilterProperty;
import com.jedlab.framework.spring.service.JPARestriction;
import com.jedlab.framework.util.StringUtil;

@RestController
@RequestMapping("/api/users")
public class UserQueryController extends AbstractQueryRestController<UserEntity, UserResponse> {

	UserService userService;
	UserQueryModelMapper userQueryModelMapper;

	public UserQueryController(UserService service) {
		super(service);
		this.userService = service;
		this.userQueryModelMapper = new UserQueryModelMapper();
		super.setEntityModelMapper(userQueryModelMapper);
	}

	@Override
	protected JPARestriction getRestriction(List<FilterProperty> filterProperties, HttpServletRequest request) {
		String uname = request.getParameter("username");
		return new UserRestriction(uname);
	}

	public static class UserRestriction implements JPARestriction {

		private String username;

		public UserRestriction(String username) {
			this.username = username;
		}

		@Override
		public Specification countSpec(CriteriaBuilder builder, CriteriaQuery criteria, Root root) {
			return (rootEntity, query, criteriaBuilder) -> applyFilter(rootEntity, query, criteriaBuilder);
		}

		@Override
		public Specification listSpec(CriteriaBuilder builder, CriteriaQuery criteria, Root root) {
			return (rootEntity, query, criteriaBuilder) -> {
				root.fetch("roles", JoinType.LEFT);
				return applyFilter(rootEntity, query, criteriaBuilder);
			};
		}

		private Predicate applyFilter(Root rootEntity, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
			var predicateList = new ArrayList<Predicate>();
			if (StringUtil.isNotEmpty(username))
				predicateList.add(criteriaBuilder.equal(rootEntity.get("username"), username));
			return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
		}

	}

	public static class UserQueryModelMapper implements EntityModelMapper<UserEntity, UserResponse> {

		@Override
		public UserResponse toModel(UserEntity entity) {
			return new UserResponse(entity.getId(), entity.getUsername());
		}

	}

}
