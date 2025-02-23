package com.phx.productcatalogueservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String getMessage(){
        return "Welcome to Springboot project";
    }

    @GetMapping("{id}")
    public String getNumberDivisibleById(@PathVariable Long id){
        if(id <= 0){
            throw new NullPointerException(("ID cannot be negative"));
        }

        return "Welcome to Springboot project";
    }
}