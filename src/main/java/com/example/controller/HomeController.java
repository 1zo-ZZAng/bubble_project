package com.example.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    //127.0.0.1:9090/bubble_bumul/login.bubble
    @GetMapping(value = "/login.bubble")
    public String loginGET(){
        return "login";
    }

    //임포트 shift + alt + o
     //127.0.0.1:9090/bubble_bumul/home.bubble
    @GetMapping(value = {"/home.bubble", "/"}) 
    public String homeGET(Model model, @AuthenticationPrincipal User user) {  
        if(user != null){
            System.out.println(user.toString());
        }
        //request.setAttribute("key", "value")
        model.addAttribute("user", user);
        return "home";
    }

    //127.0.0.1:9090/bubble_bumul/403page.bubble
    @GetMapping(value="/403page.bubble")
    public String PageGET(){
     return "/error/403page";
    }

    
}
