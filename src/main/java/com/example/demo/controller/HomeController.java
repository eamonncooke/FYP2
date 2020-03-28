package com.example.demo.controller;

import com.example.demo.model.AuthUser;
import com.example.demo.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @Autowired
    MyUserDetailsService service;

    @RequestMapping("/")
    public ModelAndView home(ModelMap model) {
        AuthUser user = service.getUserForCurrentUser(getLoggedInUserName());
        model.addAttribute("user", user);
        return new ModelAndView("/home", model);
    }

    @RequestMapping("/login")
    public ModelAndView loginPage() {
        return new ModelAndView("/login");
    }

    @RequestMapping("/logout-success")
    public ModelAndView logoutPage() {
        return new ModelAndView("/logout");
    }

    private String getLoggedInUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

}
