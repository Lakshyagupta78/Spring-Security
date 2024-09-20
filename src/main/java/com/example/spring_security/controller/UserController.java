package com.example.spring_security.controller;

import com.example.spring_security.entity.User;
import com.example.spring_security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService us;

//    @Autowired
//    private TokenBlacklistService tokenBlacklistService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user){
        return us.registerUser(user);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody User user){
        return us.verifyUser(user);
    }


//    @PostMapping("/logout")
//    public String logout(HttpServletRequest request) {
//        String authHeader = request.getHeader("Authorization");
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            String token = authHeader.substring(7);
//            tokenBlacklistService.addTokenToBlacklist(token);
//            return "Logged out successfully.";
//        }
//        return "Token not provided.";
//    }

    //Chat Gpt

}
