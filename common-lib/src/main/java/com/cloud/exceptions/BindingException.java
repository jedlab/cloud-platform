package com.cloud.exceptions;

import java.util.ArrayList;

import com.jedlab.framework.spring.validation.ErrorMessage;

/**
 * @author Omid Pourhadi
 *
 */
public class BindingException extends RuntimeException
{

    private ArrayList<ErrorMessage> errors = new ArrayList<>();

    public BindingException()
    {
        super();
    }

    public BindingException(String message)
    {
        super(message);
    }

    public BindingException(String message, int code)
    {
        super(message);
    }

    public void addError(int code, String errorMessage)
    {
        this.errors.add(new ErrorMessage(code, errorMessage));
    }

	public ArrayList<ErrorMessage> getErrors() {
		return errors;
	}
    
    

}
