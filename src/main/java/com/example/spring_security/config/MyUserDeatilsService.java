package com.example.spring_security.config;

import com.example.spring_security.entity.User;
import com.example.spring_security.entity.UserPrincipal;
import com.example.spring_security.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDeatilsService implements UserDetailsService {

    @Autowired
    private UserRepo ur;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  ur.findByUsername(username);
        if(user == null){
            System.out.println("User not found " + username);
            throw new UsernameNotFoundException("user not found");
        }

        return new UserPrincipal(user);
    }
}
