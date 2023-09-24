package com.andretavares.testesecurity.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andretavares.testesecurity.entities.CategoriaModel;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    
    @PostMapping
    public CategoriaModel postCategoria(@RequestBody CategoriaModel categoria){

        return null;

    }

    @GetMapping
    public CategoriaModel getCategoria(@RequestParam("nome") String nome){
        
        return null;

    }

    @GetMapping("/list")
    public List<CategoriaModel> getListCategoria(){
        return null;
    }

    @PutMapping
    public CategoriaModel putCategoria(@RequestParam("id") Long id){

        return null;

    }

    @DeleteMapping
    public CategoriaModel deleteCategoria(@RequestParam("id") Long id){
        
        return null;

    }

}
