package com.cloud.web;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cloud.exceptions.NotAuthorizedException;
import com.jedlab.framework.exceptions.ServiceException;
import com.jedlab.framework.spring.validation.BindingErrorMessage;
import com.jedlab.framework.spring.validation.BindingValidationError;
import com.jedlab.framework.spring.validation.ErrorMessage;
import com.jedlab.framework.spring.validation.ValidationUtil;
import com.jedlab.framework.util.CollectionUtil;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {

	MessageSource messageSource;

	public CommonExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(BindingValidationError.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public BindingErrorMessage handleFormValidationError(BindingValidationError validationError) {

		BindingErrorMessage bem = new BindingErrorMessage();

		List<ObjectError> errors = validationError.getErrors();

		ValidationUtil vu = new ValidationUtil(messageSource);

		vu.processFieldErrors(errors, bem);

		vu.processGlobalErrors(errors, bem);

		return bem;
	}

	@ExceptionHandler(ServiceException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public BindingErrorMessage handleServiceException(ServiceException se) {

		BindingErrorMessage bem = new BindingErrorMessage();
		bem.getErrors().add(new ErrorMessage(9999, se.getMessage()));

		return bem;
	}
	
	
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public BindingErrorMessage handleServiceException(ConstraintViolationException se) {

		BindingErrorMessage bem = new BindingErrorMessage();
		Set<ConstraintViolation<?>> constraintViolations = se.getConstraintViolations();
		if(CollectionUtil.isNotEmpty(constraintViolations))
		{
			for (ConstraintViolation<?> constraintViolation : constraintViolations) {
				bem.getErrors().add(new ErrorMessage(8888, constraintViolation.getPropertyPath() + " " + constraintViolation.getMessage()));
			}
		}
		return bem;
	}
	
	//DataIntegrityViolationException
	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public BindingErrorMessage handleDataIntegrityViolationException(DataIntegrityViolationException se) {

		BindingErrorMessage bem = new BindingErrorMessage();
		log.info("DataIntegrityViolationException : ", se);
		bem.getErrors().add(new ErrorMessage(8888, "Unable to perform operation"));
		return bem;
	}
	
	@ExceptionHandler(NotAuthorizedException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public BindingErrorMessage handleServiceException(NotAuthorizedException se) {

		BindingErrorMessage bem = new BindingErrorMessage();
		bem.getErrors().add(new ErrorMessage(9999, se.getMessage()));

		return bem;
	}
	
	

}
