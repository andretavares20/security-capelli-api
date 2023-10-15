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

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api")
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

    @Operation(summary  = "Adiciona uma nova categoria", description  = "Envie para esse endpoint o corpo do objeto")
    @PostMapping("/categoria")
    public Categoria create(@RequestBody CategoriaDto categoriaDto){
        return categoriaService.create(categoriaDto);
    }

    @Operation(summary  = "Atualiza uma categoria pelo id", description  = "Envie para esse endpoint o corpo do objeto contendo o id")
    @PutMapping("/categoria")
    public Categoria edit(@RequestBody Categoria categoria){
        return categoriaService.edit(categoria);
    }

    @Operation(summary  = "Deleta uma categoria pelo id", description  = "Envie para esse endpoint o id da categoria que deseja deletar")
    @DeleteMapping("/categoria/{id}")
    public void deleteById(@PathVariable("id") Long id){
        categoriaService.deleteById(id);
    }

}
