package com.example.spring_security;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloController {

    @GetMapping("/")
    public String greet(HttpServletRequest request){
        return "Welcome Lakshya" + request.getSession().getId();
    }
}
