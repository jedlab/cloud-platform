package com.cloud.report;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.jedlab.framework.report.ReportHeader;
import com.jedlab.framework.spring.security.AuthenticationUtil;
import com.jedlab.framework.util.PersianDateConverter;

/**
 * @author Omid Pourhadi
 *
 */
public class DefaultReportHeader implements ReportHeader
{

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    private String title;

    public DefaultReportHeader(String title)
    {
        this.title = title;
    }

    @Override
    public String logoPath()
    {
        return null;
    }

    @Override
    public String title()
    {
        return this.title;
    }

    @Override
    public String date()
    {
        return sdf.format(new Date());
    }

    @Override
    public String username()
    {
        return AuthenticationUtil.getUsername();
    }

    @Override
    public String persianDate()
    {
        return PersianDateConverter.getInstance().GregorianToSolar(new Date(), true);
    }

}
