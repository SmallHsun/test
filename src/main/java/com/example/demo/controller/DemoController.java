package com.example.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class DemoController {


    @GetMapping("/hello")
    public String hello(Model model){
        model.addAttribute("message", "123456 1001  commit!");
        return "hello";
    }
}
