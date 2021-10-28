package com.cloud.exceptions;

/**
 * @author Omid Pourhadi
 *
 */
public class ApiException extends RuntimeException
{

    private int code;

    public ApiException()
    {
        super();
    }

    public ApiException(String message)
    {
        super(message);
    }

    public ApiException(String message, int code)
    {
        super(message);
        this.code = code;
    }

    public int getCode()
    {
        return code;
    }

}
