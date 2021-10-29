package com.cloud.userservice.app.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.userservice.app.domain.UserEntity;
import com.cloud.userservice.app.service.UserService;
import com.cloud.userservice.app.validator.UserValidator;
import com.cloud.util.JsonUtil;
import com.jedlab.framework.spring.rest.AbstractHomeRestController;
import com.jedlab.framework.spring.rest.EntityModelMapper;

@RestController
@RequestMapping("/users")
public class UserHomeController extends AbstractHomeRestController<UserEntity, String> {

	UserService userService;
	UserHomeEntityModelMapper entityModelMapper;
	PasswordEncoder encoder;
	UserValidator userValidator;

	public UserHomeController(UserService service, PasswordEncoder encoder, UserValidator userValidator) {
		super(service);
		this.userService = service;
		this.encoder = encoder;
		this.userValidator = userValidator;
		setEntityModelMapper(new UserHomeEntityModelMapper());
	}

	private class UserHomeEntityModelMapper implements EntityModelMapper<String, UserEntity> {

		@Override
		public UserEntity toModel(String entity) {
			UserEntity userEntity = JsonUtil.toObject(entity, UserEntity.class);
			if(userEntity.getPassword() != null)
				userEntity.setPassword(encoder.encode(userEntity.getPassword()));
			return userEntity;
		}

	}
	
	@Override
	protected Validator getValidator() {
		return userValidator;
	}

}
