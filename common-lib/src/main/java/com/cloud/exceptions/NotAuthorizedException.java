package com.cloud.exceptions;

/**
 * @author Omid Pourhadi
 *
 */
public class NotAuthorizedException extends RuntimeException
{

    private int code;

    public NotAuthorizedException()
    {
        super();
    }

    public NotAuthorizedException(String message)
    {
        super(message);
    }

    public NotAuthorizedException(String message, int code)
    {
        super(message);
        this.code = code;
    }

    public int getCode()
    {
        return code;
    }

}
