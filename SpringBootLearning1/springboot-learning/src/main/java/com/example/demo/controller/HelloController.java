package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello Spring Boot!";
    }

    @GetMapping("/name")
    public String getName(){
        return "YourName";
    }

    @GetMapping("/school")
    public String getSchool(){
        return "YourSchool";
    }
}
