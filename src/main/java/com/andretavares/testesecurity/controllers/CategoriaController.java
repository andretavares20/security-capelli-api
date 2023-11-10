package com.andretavares.testesecurity.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andretavares.testesecurity.entities.Categoria;
import com.andretavares.testesecurity.services.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/client")
public class CategoriaController {
    
    @Autowired
    private CategoriaService categoriaService;

    @Operation(summary  = "Retorna todas as categorias existentes no banco de dados")
    @GetMapping("/categoria")
    public List<Categoria> findAll(){
        return categoriaService.findAll();
    }

    @Operation(summary  = "Retorna uma categoria pelo id", description  = "Envie para esse endpoint o id da categoria")
    @GetMapping("/categoria/{id}")
    public Categoria findById(@PathVariable("id") Long id){
        return categoriaService.findById(id);
    }

    @GetMapping("buscar-por-nome/categoria/{nome}")
    public Categoria findByNome(@PathVariable("nome") String nome){
        return categoriaService.findByNome(nome);
    }

}
