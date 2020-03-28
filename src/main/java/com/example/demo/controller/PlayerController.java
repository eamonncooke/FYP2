/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controller;

import com.example.demo.model.Player;
import com.example.demo.model.Testing;
import com.example.demo.service.ServiceForPlayer;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author cooke
 */
@RestController
@RequestMapping("/player")
@SessionAttributes("player")
public class PlayerController {

    @Autowired
    ServiceForPlayer service;

//    @GetMapping("")
//    public ModelAndView getPlayers(ModelMap model) {
//        System.out.println(getLoggedInUserName());
//        Player player = service.getPlayersByUserID(service.getUserByEmail(getLoggedInUserName()));
//        model.addAttribute(player);
//        player.getAuthUserId().getFirstName();
//        return new ModelAndView("/playerPage", "user", model);
//    }
    @GetMapping("/viewResults")
    public ModelAndView getTestResults(ModelMap model) {
        return new ModelAndView("/viewTestResultsList", "testList", service.getAllTest());
    }

    private String getLoggedInUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @RequestMapping("/insertTest")
    public ModelAndView insertTest(ModelMap model) {
        int testingId = service.getNewTestingNum();
        //model.addAttribute("playerID", player);
        model.addAttribute("testingID", testingId);
        model.addAttribute("testResult",new Testing());
        
        return new ModelAndView("/insertTestResults", model);
    }
    
    @PostMapping("/insertNewTest")
    public ModelAndView editAgent(@Valid @ModelAttribute("testResult") Testing test, BindingResult result, ModelMap model) {
        System.out.println(test.getDate());
        Player player = service.getPlayersByUserId(service.getUserByEmail(getLoggedInUserName()));
        test.setPlayerID(player);
        System.out.println(player.getAuthUserId().getFirstName());
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return new ModelAndView("redirect:/player/viewResults");
        }
        service.addNewTest(test);
        return new ModelAndView("redirect:/player/viewResults");
    }
//    @PostMapping("/addNewTestResults")
//    public ModelAndView editAgent(@Valid @ModelAttribute("brewery") Testing test, BindingResult result, ModelMap model) {
//        
//        if (result.hasErrors()) {
//            System.out.println(result.getAllErrors());
//            return new ModelAndView("/editBreweries");
//        }
//        service.editBreweries(brewery);
//        return new ModelAndView("redirect:/home");
//    }
}
