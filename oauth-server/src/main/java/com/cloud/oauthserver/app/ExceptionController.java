package com.cloud.oauthserver.app;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.jedlab.framework.spring.validation.BindingErrorMessage;
import com.jedlab.framework.spring.validation.ErrorMessage;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(InvalidGrantException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public BindingErrorMessage handleServiceException(InvalidGrantException se) {

		BindingErrorMessage bem = new BindingErrorMessage();
		bem.getErrors().add(new ErrorMessage(9999, se.getMessage()));

		return bem;
	}

}
