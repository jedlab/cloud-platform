package com.cloud.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jedlab.framework.exceptions.ServiceException;
import com.jedlab.framework.util.JalaliCalendar;
import com.jedlab.framework.util.PersianDateConverter;

/**
 * @author Omid Pourhadi
 *
 */
public final class DateUtil
{

    private DateUtil()
    {
    }

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    private static final SimpleDateFormat[] sdf_formats = { new SimpleDateFormat("yyyy/MM/dd"), new SimpleDateFormat("yyyy/M/d"),
            new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"), new SimpleDateFormat("yyyy-MM-dd"), new SimpleDateFormat("yyyy-M-d"),
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") };

    public final static Date toDate(String date)
    {
        for (SimpleDateFormat simpleDateFormat : sdf_formats)
        {
            try
            {
                return simpleDateFormat.parse(date);
            }
            catch (ParseException e)
            {
                // DONOTHING
            }
        }
        return null;
    }

    public final static Date addDate(Date date, int field, int amount)
    {
        Calendar cal = new JalaliCalendar();
        cal.setTime(date);
        cal.add(field, amount);
        return cal.getTime();
    }

    public static Date truncateDateTime(Date date)
    {
        if (date == null) return date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static int exactSolarMonth(Date d)
    {
        PersianDateConverter pd = PersianDateConverter.getInstance();
        String solar = pd.GregorianToSolar(d, false);
        final Pattern pattern = Pattern.compile("/(.+?)/");
        final Matcher matcher = pattern.matcher(solar);
        matcher.find();
        return Integer.parseInt(matcher.group(1));
    }

    public static int exactSolarYear(Date d)
    {
        PersianDateConverter pd = PersianDateConverter.getInstance();
        String solar = pd.GregorianToSolar(d, false);
        final Pattern pattern = Pattern.compile("(.+?)/(.+?)/(.+?)");
        final Matcher matcher = pattern.matcher(solar);
        matcher.find();
        return Integer.parseInt(matcher.group(1));
    }

    public static int getSolarYear(String gregorianYear)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.YEAR, Integer.parseInt(gregorianYear));
        c.set(Calendar.MONTH, Calendar.APRIL);
        c.set(Calendar.DATE, 1);
        return exactSolarYear(c.getTime());
    }

    public static int extractYear(Date d)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.YEAR);
    }

    public static int extractMonth(Date d)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.MONTH);
    }

    public static Date importDateTime()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.YEAR, 2000);
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date startDateTime(Date d)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date endDateTime(Date d)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 59);
        return calendar.getTime();
    }


    public static Date addMonth(Date d,int month)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.MONTH,month);
        return calendar.getTime();
    }

    public static String subtract(Date d1, Date d2)
    {
        // Calendar c1 = Calendar.getInstance();
        // c1.setTime(d1);
        // Calendar c2 = Calendar.getInstance();
        // c2.setTime(d2);
        // long mil = d2.getTime() - d1.getTime();
        LocalDate date1 = d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate date2 = d2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period p = Period.between(date1, date2);
        StringBuilder sb = new StringBuilder();
        if (p.getYears() > 0) sb.append(p.getYears()).append(" \u0633\u0627\u0644 ");
        if (p.getMonths() > 0) sb.append(p.getMonths()).append(" \u0645\u0627\u0647 ");
        if (p.getDays() > 0) sb.append(p.getDays()).append(" \u0631\u0648\u0632 ");
        return sb.toString();
    }

    public static MonthDateRange dateMonthRange(Date d)
    {
        int soalrMonth = exactSolarMonth(d);
        String m = soalrMonth < 10 ? "0" + soalrMonth : "" + soalrMonth;
        String startMonth = exactSolarYear(d) + "/" + m + "/" + "01";
        String endMonth = exactSolarYear(d) + "/" + m + "/" + "30";
        if (soalrMonth <= 6) endMonth = exactSolarYear(d) + "/" + m + "/" + "31";
        if (soalrMonth == 12) endMonth = exactSolarYear(d) + "/" + m + "/" + "29";
        try
        {
            Date start = sdf.parse(PersianDateConverter.getInstance().SolarToGregorian(startMonth));
            Date end = sdf.parse(PersianDateConverter.getInstance().SolarToGregorian(endMonth));
            return new MonthDateRange(start, end);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static class MonthDateRange
    {
        Date start;
        Date end;

        public MonthDateRange(Date start, Date end)
        {
            this.start = start;
            this.end = end;
        }

        public Date getStart()
        {
            return start;
        }

        public Date getEnd()
        {
            return end;
        }

    }

    /**
     * @param hhmm
     * @return
     */
    public static Date parseTime(String hhmm)
    {
        try
        {
            Date parse = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse("1990/01/01 " + hhmm + ":00");
            return parse;
        }
        catch (ParseException e)
        {
            throw new ServiceException("invalid date");
        }

    }

    public static Date parsDateTime(String hhmm, Date date)
    {
        try
        {
            final String slash = "/";
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            StringBuilder stringBuilder = new StringBuilder();
            Date parse = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                    .parse(stringBuilder.append(calendar.get(Calendar.YEAR)).append(slash).append(calendar.get(Calendar.MONTH) + 1)
                            .append(slash).append(calendar.get(Calendar.DAY_OF_MONTH)).append(" ").append(hhmm).append(":00").toString());
            return parse;
        }
        catch (ParseException e)
        {
            throw new ServiceException("invalid date");
        }

    }

    public static LocalDateTime toLocalDate(Date d)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        return LocalDateTime.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE),
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
    }
    
    public static LocalDate toLocalDateJava(Date d)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        return LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE));
    }

    public static Date setTimeToStartDate(Date date, String time)
    {
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        String[] hourMinute = time.split(":");
        if (hourMinute.length != 2) return date;

        int hour = Integer.valueOf(hourMinute[0]);
        if (hour < 0 || hour > 23) hour = 0;
        int minute = Integer.valueOf(hourMinute[1]);
        if (minute < 0 || minute > 59) minute = 0;
        dateCalendar.set(Calendar.HOUR_OF_DAY, hour);
        dateCalendar.set(Calendar.MINUTE, minute);
        dateCalendar.set(Calendar.SECOND, 0);

        return dateCalendar.getTime();
    }

    public static Date setTimeToEndDate(Date date, String time)
    {
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        String[] hourMinute = time.split(":");
        if (hourMinute.length != 2) return date;

        int hour = Integer.valueOf(hourMinute[0]);
        if (hour < 0 || hour > 23) hour = 23;
        int minute = Integer.valueOf(hourMinute[1]);
        if (minute < 0 || minute > 59) minute = 59;
        dateCalendar.set(Calendar.HOUR_OF_DAY, hour);
        dateCalendar.set(Calendar.MINUTE, minute);
        dateCalendar.set(Calendar.SECOND, 59);

        return dateCalendar.getTime();
    }

    public static Date setTimeToDate(Date date, Date time)
    {
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        Calendar timeCalender = Calendar.getInstance();
        timeCalender.setTime(time);
        dateCalendar.set(Calendar.HOUR_OF_DAY, timeCalender.get(Calendar.HOUR_OF_DAY));
        dateCalendar.set(Calendar.MINUTE,timeCalender.get(Calendar.MINUTE));
        dateCalendar.set(Calendar.SECOND, timeCalender.get(Calendar.SECOND));

        return dateCalendar.getTime();
    }

    public static int getDurationInHours(Date from, Date to)
    {
        if (from == null || to == null) return 0;

        Duration duration = Duration.between(from.toInstant(), to.toInstant());
        return (int) duration.getSeconds() / 3600;

    }

    public static int getDurationInHours(Long from, Long to)
    {
        if (from == null || to == null) return 0;

        return getDurationInHours(new Date(from), new Date(from));

    }
    public static Date getJustTime(Date d){
        DateFormat df = new SimpleDateFormat("HH:mm");
        String format = df.format(d);
        return setTimeToStartDate(new Date(),format);
    }




}
