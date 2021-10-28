package com.cloud.report;

import java.math.BigDecimal;
import java.util.Map;

import com.jedlab.framework.report.JasperDataExporter.GenerateReportInterceptor;
import com.jedlab.framework.report.JasperDataExporter.ReportStyleManager;
import com.cloud.util.AmountUtil;
import com.jedlab.framework.report.ReportItem;

import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;

public class DecimalPrecision implements GenerateReportInterceptor
{

    @Override
    public void onRunReport(ColumnBuilder columnBuilder, FastReportBuilder frb, ReportItem ri)
    {
        CustomExpression expr = new CustomExpression() {

            @Override
            public String getClassName()
            {
                return String.class.getName();
            }

            @Override
            public Object evaluate(Map fields, Map variables, Map parameters)
            {
                Object value = fields.get(ri.getFieldName());
                if (value == null)
                    return null;
                return AmountUtil.formatDouble(((BigDecimal) value).doubleValue());
            }
        };

        columnBuilder.setCustomExpression(expr).setTitle(ri.getTitle()).setColumnProperty(ri.getFieldName(), String.class.getName())
                .setWidth(ri.getWidth()).setStyle(ReportStyleManager.INSTACE.cellStyle());
        frb.addColumn(columnBuilder.build());
    }

}