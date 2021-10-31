package com.cloud.userservice.app.controller;

import com.cloud.entity.UserEntity;
import com.cloud.userservice.app.model.UserResponse;
import com.jedlab.framework.spring.rest.EntityModelMapper;

public class UserQueryModelMapper implements EntityModelMapper<UserEntity, UserResponse> {

	@Override
	public UserResponse toModel(UserEntity entity) {
		return new UserResponse(entity.getId(), entity.getUsername());
	}

}