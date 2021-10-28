package com.cloud.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jfree.util.ArrayUtilities;

/**
 * @author Omid Pourhadi
 *
 */
public final class QueryUtil
{

    public static String addPrimePaginatoin(String query, Integer pageSize, Integer first)
    {
        int pageNumber = (first / pageSize) + 1;
        int max = ((pageNumber * pageSize) + 1);
        int firstResult = (((pageNumber - 1) * pageSize) );
        return addPaginatoin(query, max, firstResult);
    }

    /**
     * <p>
     * pass final query to add oracle pagination to your main query
     * </p>
     * 
     * @param query
     * @param maxResult
     * @param firstResult
     * @return
     */
    public static String addPaginatoin(String query, Integer maxResult, Integer firstResult)
    {
        StringBuilder pgQ = new StringBuilder();        
        pgQ.append(query.toString());
        if (maxResult != null)
        {
            pgQ.append(" limit ").append(maxResult);
        }        
        if (firstResult != null)
        {
            if (firstResult > 0)
            {
                pgQ.append(" offset ").append(firstResult);
            }
        }

        return pgQ.toString();
    }

    public static boolean isColumnAvailable(String columnName, ResultSet rs)
    {
        boolean flag = false;
        try
        {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++)
            {
                String columnLabel = metaData.getColumnLabel(i);
                if (columnName.equalsIgnoreCase(columnLabel))
                {
                    flag = true;
                    break;
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * due to  limitation in maximun number IN query
     * @param result
     * @return
     */
    public static <T> List<List<T>> pgSubList(List<T> result)
    {
        int size = result.size();
        List<List<T>> arr = new ArrayList<>();
        if (size < 999)
        {
            arr.add(result);
            return arr;
        }
        for (int i = 0; i < size; i += 999)
        {
            int sub=i+999;
            if(sub > size)
                sub = size;
            List<T> subList = result.subList(i, sub);
            arr.add(subList);
        }
        return arr;
    }
    
    
    public  static <T> String commaSeperator(List<T> list)
    {
    	StringBuilder sb = new StringBuilder();
    	for(T t : list)
    	{
    		if(list.indexOf(t) != list.size()-1)
    			sb = sb.append(t).append(",");
    		else
    			sb = sb.append(t);
    	}
    	
    	return sb.toString();
    }
    
}
