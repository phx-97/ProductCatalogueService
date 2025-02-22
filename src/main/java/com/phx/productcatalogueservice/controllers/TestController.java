package com.phx.productcatalogueservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String getMessage(){
        return "Welcome to Springboot project";
    }
}