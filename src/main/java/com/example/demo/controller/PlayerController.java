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
//import javastrava.api.API;
//import javastrava.auth.AuthorisationService;
//import javastrava.auth.TokenManager;
//import javastrava.auth.impl.AuthorisationServiceImpl;
//import javastrava.auth.model.Token;
//import static javastrava.auth.ref.AuthorisationScope.ACTIVITY_READ_ALL;
//import javastrava.model.StravaAthlete;
//import javastrava.service.Strava;


import javax.validation.Valid;
import javax.ws.rs.QueryParam;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Value("${strava.client_id}")
    private Integer client_id;
    
    @Value("${strava.client_secret}")
    private String client_secret;
    
    @Value("${strava.appURL}")
    private String appURL;
    
    @Value("${strava.authURL}")
    private String authURL;
    
    @Value("${strava.scope}")
    private String scope;
    
    @Autowired
    ServiceForPlayer service;

    @GetMapping("/viewResults")
    public ModelAndView getTestResults(ModelMap model) {
        return new ModelAndView("/viewTestResultsList", "testList", service.getAllTest());
    }

    @GetMapping("/viewFitnessTestChart")
    public ModelAndView getFitnessTestChart(ModelMap model) {
        JSONArray testResults = chartData();
        return new ModelAndView("/fitnessChart", "testResults", testResults);
    }

    @RequestMapping("/insertTest")
    public ModelAndView insertTest(ModelMap model) {
        int testingId = service.getNewTestingNum();
        //model.addAttribute("playerID", player);
        model.addAttribute("testingID", testingId);
        model.addAttribute("testResult", new Testing());

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
        String authUrl = authURL + "?client_id="
                + Integer.toString(client_id) + "&response_type=code&redirect_uri=" + appURL + "&approval_prompt=force&scope="
                + scope;
        if (getLoggedInPlayer().getStravaActive()==null) {
            model.addAttribute("authUrl", authUrl);
            return new ModelAndView("/stravaActivities", model);
        } else {
//            Token token = TokenManager.instance().retrieveTokenWithExactScope(1, ACTIVITY_READ_ALL );
//            API api = new API(token);
//            StravaAthlete athlete = api.getAthlete(token.getAthlete().getId());
//            
            return new ModelAndView("/viewTestResultsList", "trainingList", service.getAllTest());
        }
    }

    @GetMapping("/addStravaAccount")
    public ModelAndView addStravaCode(@QueryParam("code") String code, @QueryParam("scope") String scope, @QueryParam("error") String error, ModelMap model) {
        
        if(error != null)
            return new ModelAndView("redirect:/activitiesPage");
        else{
            Player player = getLoggedInPlayer();
            
//            AuthorisationService authService = new AuthorisationServiceImpl();
//            Token token = authService.tokenExchange(client_id, client_secret, code);
//            StravaAthlete stravaAthlete = token.getAthlete();
//            
            player.setStravaActive("Activated");
            //player.setStravaUserId(stravaAthlete.getId());
            
            service.addStravaToPlayer(player);
            return new ModelAndView("redirect:/activitiesPage");
        }
    }
    
    private JSONArray chartData() {
        List<Testing> testList = service.getAllTest();
        String name;
        String newDate, month, year;
        JSONArray ja = new JSONArray();
        for (Testing test : testList) {
            month = Integer.toString(test.getDate().getMonth() + 1);
            year = Integer.toString(test.getDate().getYear() + 1900);

            newDate = month + "-" + year;
            name = test.getPlayerId().getAuthUserId().getFirstName() + " " + test.getPlayerId().getAuthUserId().getSurname();

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

    private Player getLoggedInPlayer() {
        Player player;
        return player = service.getPlayersByUserId(service.getUserByEmail(getLoggedInUserName()));
    }
}
