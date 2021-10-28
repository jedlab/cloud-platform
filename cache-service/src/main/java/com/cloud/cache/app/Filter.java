package com.cloud.cache.app;

import java.io.Serializable;

public class Filter implements Serializable
{
    private String key;
    private String operator;
    private String value;

    public Filter()
    {
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getOperator()
    {
        return operator;
    }

    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

}