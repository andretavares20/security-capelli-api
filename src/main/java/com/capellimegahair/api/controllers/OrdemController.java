package com.capellimegahair.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capellimegahair.api.services.OrdemService;

@RestController
@RequestMapping("/api")
public class OrdemController {

    @Autowired
    private OrdemService ordemService;
    
    

}
