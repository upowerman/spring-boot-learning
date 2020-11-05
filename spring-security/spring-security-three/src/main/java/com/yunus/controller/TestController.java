package com.yunus.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gaoyunfeng
 */
@RestController
public class TestController {

    @GetMapping("hello")
    public String hello() {
        return "hello spring security";
    }

    @GetMapping("index")
    public Object index(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}