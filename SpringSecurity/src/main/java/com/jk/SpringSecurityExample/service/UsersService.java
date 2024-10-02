package com.jk.SpringSecurityExample.service;

import com.jk.SpringSecurityExample.module.Users;
import com.jk.SpringSecurityExample.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
@Autowired
    private UserRepo userRepo;

@Autowired
private AuthenticationManager authenticationManager;

@Autowired
private JWTService jwtService;

private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    public Users register(Users user) {

        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
        return user;
    }

    public String verifey(Users user) {
//        try {
            Authentication authentication =
                    authenticationManager.
                            authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));

            if (authentication.isAuthenticated()) {
//                return "success";
//      generate the token by using JWT

                return jwtService.generate(user.getName());


            }
            return "fail";
//        } catch (Exception e) {
//            return "fail";
//        }
    }
}
