/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service;

import com.example.demo.model.UserPrincipal;
import com.example.demo.model.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author cooke
 */
@Service
public class MyUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository repo;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        AuthUser user = repo.findByEmail(email);
        if(user == null)
            throw new UsernameNotFoundException("User not found");
        return new UserPrincipal(user);
    }
    
    public AuthUser getUserForCurrentUser(String email){
        AuthUser user = repo.findByEmail(email);
        return user;
    }
}
