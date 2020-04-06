/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controller;

import com.example.demo.model.Player;
import com.example.demo.model.StravaVars;
import com.example.demo.model.Testing;
import com.example.demo.service.ServiceForPlayer;
import java.util.List;
import javastrava.api.v3.auth.AuthorisationService;
import javastrava.api.v3.auth.impl.retrofit.AuthorisationServiceImpl;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.auth.model.TokenResponse;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.rest.AuthorisationAPI;
import javastrava.api.v3.service.Strava;
import javax.validation.Valid;
import javax.ws.rs.QueryParam;
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

    @GetMapping("/viewResults")
    public ModelAndView getTestResults(ModelMap model) {
        return new ModelAndView("/viewTestResultsList", "testList", service.getAllTest());
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
        Player player = getLoggedInPlayer();
        test.setPlayerId(player);
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return new ModelAndView("redirect:/player/viewResults");
        }
        service.addNewTest(test);
        return new ModelAndView("redirect:/player/viewResults");
    }
    
    @RequestMapping("/activitiesPage")
    public ModelAndView activitiesPage(ModelMap model) {
        String authUrl = StravaVars.stravaAuthURL+"?client_id="
				+ StravaVars.clientId +"&response_type=code&redirect_uri="+StravaVars.appURL+"&approval_prompt=force&scope="
				+ "profile:write,activity:read_all";
        if(getLoggedInPlayer().getStravaActive()==null){
            model.addAttribute("authUrl", authUrl);
            return new ModelAndView("/stravaActivities", model);
        }
        else
            return new ModelAndView("/viewTestResultsList","testList", service.getAllTest());
    }
    
    @GetMapping("/addStravaAccount")
    public ModelAndView addStravaCode(@QueryParam("code") String code, @QueryParam("scope") String scope, ModelMap model) {
        Player player = getLoggedInPlayer();
        AuthorisationService authService = new AuthorisationServiceImpl();
        Token token = authService.tokenExchange(Integer.parseInt(StravaVars.clientId), StravaVars.clientSecret, code);
        Strava strava = new Strava(token);
        player.setStravaUserId(strava.getAuthenticatedAthlete().getId());
        player.setStravaActive("Activated");
       
        return new ModelAndView("redirect:/");
    }
    
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
            name = test.getPlayerId().getAuthUserId().getFirstName()+" " + test.getPlayerId().getAuthUserId().getSurname();
            
            JSONObject jo = new JSONObject();
            jo.put("name", name);
            jo.put("date", newDate);
            jo.put("time", Integer.toString(test.getTime()));
            jo.put("postion", test.getPlayerId().getPostion());
            
            ja.put(jo);
        }
        return ja;
    }
    private String getLoggedInUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
    
    private Player getLoggedInPlayer(){
        Player player;
        return player = service.getPlayersByUserId(service.getUserByEmail(getLoggedInUserName()));
    }
}
