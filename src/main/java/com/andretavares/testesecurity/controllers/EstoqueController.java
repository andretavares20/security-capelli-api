package com.andretavares.testesecurity.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andretavares.testesecurity.entities.Estoque;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {
    
    @PutMapping
    public ResponseEntity<?> putEstoque(@RequestParam("produtoId") Long produtoId,
        @RequestParam("quantidade") Long quantidade){

        return null;

    }

    @GetMapping
    public Long buscaQuantidadeProdutoEstoque(@RequestParam("produtoId") Long produtoId){

        return null;

    }

    @GetMapping("/list")
    public List<Estoque> listEstoque(){
        
        return null;
        
    }

}
