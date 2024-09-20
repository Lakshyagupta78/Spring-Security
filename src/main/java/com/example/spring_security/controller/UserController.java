package com.example.spring_security.controller;

import com.example.spring_security.entity.User;
import com.example.spring_security.service.JWTService;
import com.example.spring_security.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService us;

    @Autowired
    private JWTService jwtService;

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

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            jwtService.invalidateToken(token);
            return ResponseEntity.ok("Logged out successfully, token is now invalidated.");
        }
        return ResponseEntity.badRequest().body("Token not found.");
    }

}
