package com.example.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class DemoController {


    @GetMapping("/hello")
    public String hello(Model model){
        model.addAttribute("message", "Hello World!0930");
        return "hello";
    }
}
