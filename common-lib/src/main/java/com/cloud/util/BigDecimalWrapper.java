package com.cloud.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;

public class BigDecimalWrapper
{

    private static final int ZERO = 0;
    private BigDecimal n1;
    private BigDecimal n2;

    public BigDecimalWrapper(BigDecimal n1, BigDecimal n2)
    {
        Objects.requireNonNull(n1);
        Objects.requireNonNull(n2);
        this.n1 = n1;
        this.n2 = n2;
    }

    public BigDecimalWrapper(BigDecimal n1)
    {
        Objects.requireNonNull(n1);
        this.n1 = n1;
    }

    public BigDecimal add()
    {
        try
        {
            return new BigDecimal(new DecimalFormat("#.##").format(n1.add(n2)));
        }
        catch (ArithmeticException se)
        {
            MathContext mc = new MathContext(2, RoundingMode.HALF_UP);
            return n1.add(n2, mc);
        }
    }

    public BigDecimal subtract()
    {
        try
        {
            return new BigDecimal(new DecimalFormat("#.##").format(n1.subtract(n2)));
        }
        catch (ArithmeticException se)
        {
            MathContext mc = new MathContext(2, RoundingMode.HALF_UP);
            return n1.subtract(n2, mc);
        }
    }

    public BigDecimal divide()
    {
        if (new BigDecimalWrapper(n2).eq(BigDecimal.ZERO))
            return BigDecimal.ZERO;
        try
        {
            return new BigDecimal(new DecimalFormat("#.##").format(n1.divide(n2)));
        }
        catch (ArithmeticException se)
        {
            MathContext mc = new MathContext(2, RoundingMode.HALF_UP);
            return n1.divide(n2, mc);
        }
    }

    public BigDecimal multiply()
    {
        try
        {
            return new BigDecimal(new DecimalFormat("#.##").format(n1.multiply(n2)));
        }
        catch (ArithmeticException se)
        {
            MathContext mc = new MathContext(2, RoundingMode.HALF_UP);
            return n1.multiply(n2, mc);
        }
    }

    public BigDecimal round()
    {
        return n1.setScale(2, RoundingMode.HALF_UP);
    }

    public boolean gt(BigDecimal decimal)
    {
        return n1.compareTo(decimal) > ZERO;
    }

    public boolean gte(BigDecimal decimal)
    {
        return n1.compareTo(decimal) >= ZERO;
    }

    public boolean lt(BigDecimal decimal)
    {
        return n1.compareTo(decimal) < ZERO;
    }

    public boolean lte(BigDecimal decimal)
    {
        return n1.compareTo(decimal) <= ZERO;
    }

    public boolean eq(BigDecimal decimal)
    {
        return n1.compareTo(decimal) == ZERO;
    }

}