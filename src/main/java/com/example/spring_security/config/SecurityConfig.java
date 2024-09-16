package com.example.spring_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {

//        http.csrf(customizer -> customizer.disable());  //We are disabling the csrf(Cross-Site Request Forgery)
//        http.authorizeHttpRequests(request -> request.anyRequest().authenticated()); //No one should be able to access the page without authentication.
////        http.formLogin(Customizer.withDefaults()); //For Browser (Login form)
//        http.httpBasic(Customizer.withDefaults()); //For postman (Login)
//        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
////      This is commonly used in REST APIs where the client sends all necessary information (like authentication tokens) with every request, typically in headers (e.g., JWT tokens).
//        return http.build();

        //Another way to write this code
         return http
                  .csrf(customizer -> customizer.disable())  //We are disabling the csrf(Cross-Site Request Forgery)
                  .authorizeHttpRequests(request -> request.anyRequest().authenticated()) //No one should be able to access the page without authentication.
//                  .formLogin(Customizer.withDefaults()) //For Browser (Login form)
                  .httpBasic(Customizer.withDefaults()) //For postman (Login)
                  .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                  //This is commonly used in REST APIs where the client sends all necessary information (like authentication tokens) with every request, typically in headers (e.g., JWT tokens).
                  .build();
    }
}
