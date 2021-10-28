package com.cloud.util;

import java.math.BigDecimal;

import org.omidbiz.core.axon.Axon;
import org.omidbiz.core.axon.AxonBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Omid Pourhadi
 *
 */
public class Constants
{

    public static final int BATCH_SIZE = 500;

  

    // public static final String OTP_SECRET = Base32.random();

    /**
     * 3 minutes
     */
    public static final int OTP_CLOCK = 3 * 60;

    public static final String TOKEN = "SM-TOKEN";

    public static final Axon axon = new AxonBuilder().create();

    public static final ObjectMapper mapper = new ObjectMapper();

    public static final String VERSION = "4.4.6";
    
    public static final BigDecimal ZERO = BigDecimal.ZERO;
    
    
    public static final int INITIAL_PAGE = 0;
    public static final int INITIAL_PAGE_SIZE = 5;
    
    
    
    
    
    public static final String MSISDN_PATTERN = "^[9][8][9][0-9]{9}$";
    public static final String EMAIL_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";


}
