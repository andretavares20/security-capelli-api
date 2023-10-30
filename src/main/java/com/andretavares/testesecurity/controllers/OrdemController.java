package com.andretavares.testesecurity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andretavares.testesecurity.services.OrdemService;

@RestController
@RequestMapping("/api")
public class OrdemController {

    @Autowired
    private OrdemService ordemService;
    
    

}
