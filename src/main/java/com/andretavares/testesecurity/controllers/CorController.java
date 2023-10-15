package com.andretavares.testesecurity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andretavares.testesecurity.dto.CorDto;
import com.andretavares.testesecurity.entities.Cor;
import com.andretavares.testesecurity.services.CorService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/cor")
public class CorController {

    @Autowired
    private CorService corService;

    @Operation(summary  = "Crie uma nova cor", description  = "Envie para esse endpoint o corpo da cor, contendo o nome e a categoria que ela pertence.")
    @PostMapping("/adicionar-cor")
    public ResponseEntity<Cor> postCor(@RequestBody CorDto corDto) {

        return ResponseEntity.ok().body(corService.postCor(corDto));

    }
    
}
