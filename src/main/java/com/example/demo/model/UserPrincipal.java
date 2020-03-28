/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import com.example.demo.model.AuthUser;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author cooke
 */
public class UserPrincipal implements UserDetails{

    String ROLE_PREFIX = "ROLE_";
    
    private String email;
    private String password;
    private String role;
    
    public UserPrincipal(AuthUser user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole();
    }

    
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        
        list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role));
    	
    	return list;
        
    }

    @Override
    public String getPassword() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return password;
    }

    @Override
    public String getUsername() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return true;
    }

    @Override
    public boolean isEnabled() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return true;
    }
    
}
