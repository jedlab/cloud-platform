package com.cloud.util;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.jedlab.framework.util.RegexUtil;
import com.jedlab.framework.util.StringUtil;

/**
 * @author Omid Pourhadi
 *
 */
public class NumberUtil
{

    private static final Logger log = LoggerFactory.getLogger(NumberUtil.class);
    public static final String PHONE_NUMBER_PATTERN = "09\\d{9}";

    private NumberUtil()
    {
    }

    public static String msisdn(String phone)
    {
        if (StringUtil.isEmpty(phone))
        {
            log.info("phone is empty");
            return phone;
        }
        phone = phone.replaceAll("\\s", "");
        if (RegexUtil.match(RegexUtil.DIGIT_PATTERN, phone))
        {                       
            if (phone.startsWith("0"))
                return "98" + phone.substring(1);
            if (phone.startsWith("98") == false)
                return "98" + phone;
        }
        return phone;
    }

    public static String toZero(String phone)
    {
        if (RegexUtil.match(RegexUtil.DIGIT_PATTERN, phone))
        {
            if (phone.startsWith("98"))
                return "0" + phone.substring(2);
        }
        return phone;
    }

    public static BigDecimal toRial(BigDecimal bd)
    {
        if (bd == null)
            throw new IllegalArgumentException("null exception");
        return bd.multiply(BigDecimal.TEN);
    }

    public static String maskMsisdn(String phone)
    {
        if (StringUtil.isEmpty(phone))
            return phone;
        StringBuilder sb = new StringBuilder(phone.length());
        for (int i = 0; i < phone.length(); i++)
        {
            if (i == 4 || i == 5 || i == 6)
                sb.append("*");
            else
                sb.append(phone.charAt(i));
        }
        return sb.toString();
    }

    private static final Pattern p = Pattern.compile("^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$");

    public static String generateTrackerId()
    {
        String res = String.format("%s-%s-1abc-b%s-%s", RandomStringUtils.randomNumeric(8), RandomStringUtils.randomNumeric(4),
                RandomStringUtils.randomNumeric(3), String.valueOf((new Date().getTime())).substring(0, 12));
        if (p.matcher(res).matches() == false)
            res = generateTrackerId();
        return res;
    }

    public static String getRealIp(HttpServletRequest req)
    {
        if (req == null)
            return "127.0.0.1";
        String ip = req.getHeader("X-Real-IP");
        if (StringUtil.isEmpty(ip))
            ip = req.getHeader("X-Forwarded-For");
        if (StringUtil.isEmpty(ip))
            ip = req.getRemoteAddr();
        if (StringUtil.isEmpty(ip))
            ip = "127.0.0.1";
        return ip;
    }

    public static String normalize(String ip) throws UnknownHostException
    {

        return InetAddress.getByName(ip).getHostAddress();
    }

    public static String trim(String input)
    {
        if (input == null || input.isEmpty())
            return input;
        if (input.indexOf(" ") > 0)
            input = input.substring(0, input.indexOf(" "));
        if (input.length() > 10)
            input = input.substring(0, 9);
        return input + "...";
    }

    public static String removeHttps(String str)
    {
        if (str.startsWith("https"))
            return "http" + str.substring(5);
        return str;
    }

    public static String addHttps(String str)
    {
        if (str.startsWith("http"))
            return "https" + str.substring(4);
        return str;
    }

    public static boolean isGraterThanZero(BigDecimal number)
    {
        if (number == null)
            return false;
        return number.longValue() > 0l;
    }

    public static boolean isZeroOrLess(BigDecimal number)
    {
        return !isGraterThanZero(number);
    }

    public static BigDecimal toRials(BigDecimal amountInToman)
    {
        if (amountInToman == null)
            return BigDecimal.ZERO;
        return amountInToman.multiply(BigDecimal.TEN);
    }

    public static BigDecimal toTomans(BigDecimal amountInRial)
    {
        if (amountInRial == null || amountInRial.longValue() < 10)
            return BigDecimal.ZERO;
        return amountInRial.divide(BigDecimal.TEN).abs();
    }

    public static String addLeadZero(String numberString, int lengh)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lengh - numberString.length(); i++)
        {
            sb.append(0);
        }
        return sb.append(numberString).toString();

    }

    public static String generateSubscriptionCode(String msisdn)
    {
        Random rand = new Random();
        return msisdn(msisdn).substring(8) + rand.nextInt(99);
    }

    public static boolean isFirstNumberGreater(BigDecimal firstnum, BigDecimal secondNum)
    {
        return firstnum.compareTo(secondNum) > 0;
    }

    public static boolean isFirstNumberGreaterOrEqual(BigDecimal firstnum, BigDecimal secondNum)
    {
        return firstnum.compareTo(secondNum) >= 0;
    }

    public static boolean isValidPhoneNumber(String phone)
    {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        try
        {
            PhoneNumber pn = phoneNumberUtil.parse(phone, "IR");
            return true;
        }
        catch (NumberParseException e)
        {
            log.info("Invalid phone number " + phone);
            return false;
        }

    }
 

}
