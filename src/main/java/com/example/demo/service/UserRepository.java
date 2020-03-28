/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service;

import com.example.demo.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author cooke
 */

public interface UserRepository extends JpaRepository<AuthUser, Integer>{
    AuthUser findByEmail(String email);
}
