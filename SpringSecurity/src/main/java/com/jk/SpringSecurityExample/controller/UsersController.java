package com.jk.SpringSecurityExample.controller;

import com.jk.SpringSecurityExample.module.Users;
import com.jk.SpringSecurityExample.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {
@Autowired
    private UsersService service;




    @PostMapping("/register")
    public Users register(@RequestBody Users user) {


 return service.register(user);
    }


    @PostMapping("/login")
    public String login(@RequestBody Users user) {
        return service.verifey(user);
    }



    @PostMapping("/logout")
    public String logout() {
        System.out.println("logout");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            // Perform logout logic here, if needed
            SecurityContextHolder.clearContext();
        }
        return "Logged out successfully!8768767";
    }

}
