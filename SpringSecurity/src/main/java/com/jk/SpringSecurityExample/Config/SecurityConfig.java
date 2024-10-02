package com.jk.SpringSecurityExample.Config;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Autowired
    private UserDetailsService userDetailsService;


    @Autowired
    private JwtFilter jwtFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
/*

//lambda  expression
http.csrf(customizer->customizer.disable());

//without lambda expression
// Customizer<CsrfConfigurer<HttpSecurity>> custocsrf=new Customizer<CsrfConfigurer<HttpSecurity>>() {
//            @Override
//            public void customize(CsrfConfigurer<HttpSecurity> httpSecurityCsrfConfigurer) {
//
//                httpSecurityCsrfConfigurer.disable();
//            }
//        };
//
//        http.csrf(custocsrf);


http.authorizeRequests(request->request.anyRequest().authenticated());
//http.formLogin(Customizer.withDefaults());
http.httpBasic(Customizer.withDefaults());
http.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));




    return http.build();

*/

        //write the code in one pipeline

        return
                http
                        .csrf(customizer -> customizer.disable())
//                       .formLogin(Customizer.withDefaults())

                        .authorizeRequests(request -> request
                                .requestMatchers("register", "login")//mentioned url without authentication allow to access

                                .permitAll()
                                .anyRequest().authenticated())


                        .httpBasic(Customizer.withDefaults())
                        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                        //jwt Filter
                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                        .logout(logout -> logout
                                .logoutUrl("/logout") // Specify the logout URL
                                .logoutSuccessHandler((request, response, authentication) -> {
                                    // Handle successful logout, if necessary
                                    response.setStatus(HttpServletResponse.SC_OK);
                                    response.getWriter().write("Logged out successfully!");
                                })
                        )

                        .build();

    }



    /*
    //in memory authentication  when ever server start username and password generate.
    @Bean

    public UserDetailsService userDetailsService() {
        UserDetails user1= User
                .withDefaultPasswordEncoder()
                .username("nikhil")
                .password("n@123")
                .roles("User")
                .build();

        UserDetails user2= User
                .withDefaultPasswordEncoder()
                .username("pikhil")
                .password("p@123")
                .roles("User")
                .build();
        return new InMemoryUserDetailsManager(user1, user2);
    }*/



    // database authentication
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
       // authProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());//this line not encode the password
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder(12)); //this line will encode the password
        authProvider.setUserDetailsService(userDetailsService);

        return authProvider;
    }




    //jwt related configuration

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {


      return  authenticationConfiguration.getAuthenticationManager();
    }


}
