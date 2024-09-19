package com.example.spring_security.service;

import com.example.spring_security.entity.User;
import com.example.spring_security.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo ur;

    @Autowired
    private JWTService jwtservice;

    @Autowired
    private AuthenticationManager auth;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    public User registerUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return ur.save(user);
    }

    public String verifyUser(User user) {
        Authentication authentication =
                auth.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));

        if(authentication.isAuthenticated()){
            return jwtservice.genarateToken(user.getUsername());
        }
        return "Username or password is wrong";
    }
}
