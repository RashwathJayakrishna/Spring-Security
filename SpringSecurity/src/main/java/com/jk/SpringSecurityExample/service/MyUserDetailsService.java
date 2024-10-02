package com.jk.SpringSecurityExample.service;

import com.jk.SpringSecurityExample.module.UserPrincipl;
import com.jk.SpringSecurityExample.module.Users;
import com.jk.SpringSecurityExample.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users users=userRepo.findByName(username);

        if(users==null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found :"+ username);
        }
        return new UserPrincipl(users);
    }
}
