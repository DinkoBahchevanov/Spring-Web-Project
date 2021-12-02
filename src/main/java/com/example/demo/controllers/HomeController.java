package com.example.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {

    @GetMapping("/")
    public ModelAndView homePage() {
        ModelAndView mav = new ModelAndView("index.html");
        return mav;
    }
}
