package com.cloud.exceptions;

/**
 * @author Omid Pourhadi
 *
 */
public class PageNotFoundException extends RuntimeException
{

    private int code;

    public PageNotFoundException()
    {
        super();
    }

    public PageNotFoundException(String message)
    {
        super(message);
    }

    public PageNotFoundException(String message, int code)
    {
        super(message);
        this.code = code;
    }

    public int getCode()
    {
        return code;
    }

}
