package com.cloud.util;

import com.jedlab.framework.exceptions.ServiceException;

public interface Mapper<S,T>
{
    public T map(S source) throws ServiceException;
}