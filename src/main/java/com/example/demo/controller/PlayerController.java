/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controller;

import com.example.demo.model.Player;
import com.example.demo.model.Testing;
import com.example.demo.service.ServiceForPlayer;
import java.util.List;
import javax.validation.Valid;
import org.json.JSONArray;
import org.json.JSONObject;
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

    @GetMapping("/viewFitnessTestChart")
    public ModelAndView getFitnessTestChart(ModelMap model){
        JSONArray testResults = chartData();
        return new ModelAndView("/fitnessChart", "testResults",  testResults);
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
    
    private JSONArray chartData(){
        List<Testing> testList = service.getAllTest();
        String name;
        String newDate, month, year;
        JSONArray ja = new JSONArray();
        for(Testing test : testList)
        {
            month = Integer.toString(test.getDate().getMonth()+1);
            year = Integer.toString(test.getDate().getYear() + 1900);
            
            newDate = month +"-"+year;
            name = test.getPlayerID().getAuthUserId().getFirstName()+" " + test.getPlayerID().getAuthUserId().getSurname();
            
            JSONObject jo = new JSONObject();
            jo.put("name", name);
            jo.put("date", newDate);
            jo.put("time", Integer.toString(test.getTime()));
            jo.put("postion", test.getPlayerID().getPostion());
            
            ja.put(jo);
        }
        
        return ja;
    }
}
