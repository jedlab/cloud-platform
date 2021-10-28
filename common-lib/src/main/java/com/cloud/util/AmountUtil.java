package com.cloud.util;

import java.text.DecimalFormat;

public class AmountUtil
{

    
    public static String formatDoubleToDecimal(double d)
    {
        DecimalFormat df = new DecimalFormat("###,###.##");
        df.setDecimalSeparatorAlwaysShown(true);
        df.setMinimumFractionDigits(2);

        return df.format(d);
    }
    
    
    public static String formatDouble(double d)
    {
        DecimalFormat df = new DecimalFormat("###,###");
        df.setDecimalSeparatorAlwaysShown(false);
        df.setMinimumFractionDigits(0);

        return df.format(d);
    }
    
}
