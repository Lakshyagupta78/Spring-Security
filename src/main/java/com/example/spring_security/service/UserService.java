package com.example.spring_security.service;

import com.example.spring_security.entity.User;
import com.example.spring_security.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo ur;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    public User registerUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return ur.save(user);
    }
}
