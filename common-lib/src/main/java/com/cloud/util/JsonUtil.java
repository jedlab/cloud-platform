package com.cloud.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil
{

    
    private static final ObjectMapper  MAPPER = new  ObjectMapper();
    
    private JsonUtil()
    {
    }
    
    
    
    public static <T> String toJson(T type)
    {
        try
        {
            return MAPPER.writeValueAsString(type);
        }
        catch (JsonProcessingException e)
        {
            log.info("JsonProcessingException {}", e);
        }
        return "";
    }

    
    
    
}
