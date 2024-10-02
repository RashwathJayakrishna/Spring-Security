package com.jk.SpringSecurityExample.Config;

import com.jk.SpringSecurityExample.service.JWTService;
import com.jk.SpringSecurityExample.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;


    @Autowired
    private ApplicationContext applicationContext;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYXNoIiwiaWF0IjoxNzI3MDA3NzY0LCJleHAiOjE3MjcwMDc4MDB9.hfu09wyAczvenfAgakZM217pGTskCUscPnao0TjneHA

        String authHeader = request.getHeader("Authorization");
        String jwtToken = null,
                username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
            username = jwtService.extractUsername(jwtToken);
        }

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){

            UserDetails userDetails=
                    applicationContext.
                            getBean(MyUserDetailsService.class)
                            .loadUserByUsername(username);



            if(jwtService.validateToken(jwtToken,userDetails)){

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                        new UsernamePasswordAuthenticationToken
                                (userDetails,
                                        null,
                                        userDetails.getAuthorities());


                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

               SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


            }
        }
 filterChain.doFilter(request, response);
    }
}
