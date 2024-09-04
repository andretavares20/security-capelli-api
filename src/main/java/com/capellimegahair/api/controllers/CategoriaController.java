package com.capellimegahair.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capellimegahair.api.dto.CategoriaDto;
import com.capellimegahair.api.entities.Categoria;
import com.capellimegahair.api.services.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/client")
public class CategoriaController {
    
    @Autowired
    private CategoriaService categoriaService;

    @Operation(summary  = "Retorna todas as categorias ativas existentes no banco de dados")
    @GetMapping("/categoria")
    public List<CategoriaDto> findAll(){
        return categoriaService.findAllAtivas();
    }

    @Operation(summary  = "Retorna uma categoria pelo id", description  = "Envie para esse endpoint o id da categoria")
    @GetMapping("/categoria/{id}")
    public CategoriaDto findById(@PathVariable("id") Long id){
        return categoriaService.findById(id);
    }

    @GetMapping("buscar-por-nome/categoria/{nome}")
    public CategoriaDto findByNome(@PathVariable("nome") String nome){
        return categoriaService.findByNome(nome);
    }

}
