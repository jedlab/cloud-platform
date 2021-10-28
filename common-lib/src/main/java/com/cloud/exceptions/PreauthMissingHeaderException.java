package com.cloud.exceptions;

/**
 * @author Omid Pourhadi
 *
 */
public class PreauthMissingHeaderException extends RuntimeException
{

    private int code;

   

    public PreauthMissingHeaderException(String message)
    {
        super(message);
    }

    public PreauthMissingHeaderException(String message, int code)
    {
        super(message);
        this.code = code;
    }

    public int getCode()
    {
        return code;
    }

}
