package com.andretavares.testesecurity.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andretavares.testesecurity.dto.CategoriaDto;
import com.andretavares.testesecurity.entities.Categoria;
import com.andretavares.testesecurity.services.CategoriaService;

@RestController
@RequestMapping("/api")
public class CategoriaController {
    
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/categoria")
    public List<Categoria> findAll(){
        return categoriaService.findAll();
    }

    @GetMapping("/categoria/{id}")
    public Categoria findById(@PathVariable("id") Long id){
        return categoriaService.findById(id);
    }

    @PostMapping("/categoria")
    public Categoria create(@RequestBody CategoriaDto categoriaDto){
        return categoriaService.create(categoriaDto);
    }

    @PutMapping("/categoria")
    public Categoria edit(@RequestBody Categoria categoria){
        return categoriaService.edit(categoria);
    }

    @DeleteMapping("/categoria/{id}")
    public void deleteById(@PathVariable("id") Long id){
        categoriaService.deleteById(id);
    }

}
