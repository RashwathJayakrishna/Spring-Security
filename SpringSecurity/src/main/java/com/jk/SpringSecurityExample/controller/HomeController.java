package com.jk.SpringSecurityExample.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

@GetMapping("/")
    public  String greet(HttpServletRequest request) {
        return "Welcome to Spring Security Example Project Rashwath Jayakrishna \n "+request.getSession().getId();
    }
}
