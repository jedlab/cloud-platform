package com.cloud.userservice.app.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.cloud.entity.UserEntity;

@Component
public class UserValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return ClassUtils.isAssignable(clazz, UserEntity.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserEntity entity = (UserEntity)target;
		if(entity.getUsername().length() < 6)
			errors.reject("342","username can't be less than 6 characters");
	}

}
