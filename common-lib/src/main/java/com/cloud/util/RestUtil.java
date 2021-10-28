package com.cloud.util;

import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

public class RestUtil
{

    
    public static HttpHeaders jsonUtfHeaders(MultiValueMap<String, String> values)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        if(values != null)
            headers.addAll(values);
        return headers;
    }
    
    public static HttpHeaders textUtfHeaders(MultiValueMap<String, String> values)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/plain;charset=UTF-8");
        if(values != null)
            headers.addAll(values);
        return headers;
    }
    
}
