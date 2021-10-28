package com.cloud.util;

import com.jedlab.framework.exceptions.ServiceException;

public class MsisdnToStringMapper implements Mapper<Object, String>
{

    @Override
    public String map(Object source) throws ServiceException
    {
        if(source == null)
            return null;
        return source.toString();
    }

}
