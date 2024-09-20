package com.example.spring_security.config;

import com.example.spring_security.service.JWTService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//import org.apache.catalina.core.ApplicationContext;
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
    private ApplicationContext context;

//    @Autowired
//    private TokenBlacklistService tokenBlacklistService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String AuthHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if(AuthHeader != null && AuthHeader.startsWith("Bearer ")){
            token = AuthHeader.substring(7);

            //chat gpt
//            if  (tokenBlacklistService.isTokenBlacklisted(token)) {
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is expired or invalid");
//                return;
//            }
            //chat gpt


            try{
                username = jwtService.extractUserName(token);
            }catch (ExpiredJwtException e) {
                // If token is expired, let the exception handler handle it
//                System.out.println("JWT token has expired.");

                // Return a string to the client when the token is expired
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Set 401 Unauthorized status
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"JWT token has expired.\"}");
                response.getWriter().flush();
                return; // Stop further processing

            }

        }
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = context.getBean(MyUserDeatilsService.class).loadUserByUsername(username);

            if(jwtService.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
