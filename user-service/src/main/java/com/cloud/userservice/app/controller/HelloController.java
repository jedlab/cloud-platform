package com.cloud.userservice.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController
{

    
    @RequestMapping("/")
    public String home()
    {
        return "Hello World";
    }
    
    @RequestMapping("/api/hello")
    public String secureHello()
    {
        return "Hello Secure World";
    }
    
}
