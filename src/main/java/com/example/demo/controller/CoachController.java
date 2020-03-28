/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controller;

import com.example.demo.model.Player;
import com.example.demo.service.ServiceForCoach;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author cooke
 */
@RestController

@RequestMapping("/coach")
@SessionAttributes("coach")
public class CoachController {

    @Autowired
    ServiceForCoach service;

    @GetMapping("")
    public ModelAndView getPlayers(ModelMap model) {
        return new ModelAndView("/viewPlayers", "playerList", service.getAllPlayers());
    }
    @GetMapping("/viewResults")
    public ModelAndView getTestResults(ModelMap model) {
        return new ModelAndView("/viewTestResultsList", "testList", service.getAllTest());
    }
    
    private String getLoggedInUserName(){
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        
        if(principal instanceof UserDetails)
            return ((UserDetails) principal).getUsername();
        
        return principal.toString();
    }
}
