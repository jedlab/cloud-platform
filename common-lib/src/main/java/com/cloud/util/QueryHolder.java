package com.cloud.util;

import java.util.List;


/**
 * @author Omid Pourhadi
 *
 */
public class QueryHolder
{

    private String query;
    private List<Object> values;

    public QueryHolder(String query, List<Object> values)
    {
        this.query = query;
        this.values = values;
    }

    public String getQuery()
    {
        return query;
    }

    public List<Object> getValues()
    {
        return values;
    }

}
