package com.example.spring_security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    private String secretKey = "";

    public JWTService(){
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String genarateToken(String username) {
        Map<String,Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 5 * 60 * 1000)) //token Expire in 5 min
//                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 15))
//                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000 * 24)) // Token expires in 24 hours // Token expires in 24 hours
                .and()
                .signWith(getKey())
                .compact();

        //Chat gpt
//        String token = Jwts.builder()
//                .claims(claims)
//                .subject(username)
//                .issuedAt(new Date(System.currentTimeMillis()))
//                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 24)) // Token expires in 24 hours
//                .signWith(getKey())
//                .compact();
//        TokenBlacklistService.addTokenToBlacklist(token); // Add token to active tokens
//        return token;
    }

    private SecretKey getKey() {
        byte[] keybytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keybytes);
    }

    public String extractUserName(String token) {
        //extract username from token
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }
}
