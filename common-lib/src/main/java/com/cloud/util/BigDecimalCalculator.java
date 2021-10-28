package com.cloud.util;

import java.math.BigDecimal;

public class BigDecimalCalculator
{

    private BigDecimalCalculator()
    {
    }

    public static BigDecimalWrapper bd(BigDecimal n1, BigDecimal n2)
    {
        return new BigDecimalWrapper(n1, n2);
    }
    
    
    public static BigDecimalWrapper bd(BigDecimal n1, int n2)
    {
        return new BigDecimalWrapper(n1, new BigDecimal(n2));
    }
    
    
    public static BigDecimalWrapper is(BigDecimal n1)
    {
        return new BigDecimalWrapper(n1);
    }
    
    
    
}
