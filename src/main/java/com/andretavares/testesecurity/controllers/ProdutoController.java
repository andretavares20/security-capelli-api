package com.andretavares.testesecurity.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andretavares.testesecurity.dto.ProdutoDto;
import com.andretavares.testesecurity.entities.Produto;
import com.andretavares.testesecurity.services.ProdutoService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Operation(summary  = "Retorna todos os produto existentes no sistema.")
    @GetMapping("/produto")
    public List<Produto> findAll(){

        return produtoService.findAll();
    }

    @GetMapping("/produto/{id}")
    public Produto findById(@PathVariable("id") Long id){
        return produtoService.findById(id);
    }

    @PostMapping("/produto")
    public Produto create(@RequestBody ProdutoDto produtoDto){
        return produtoService.create(produtoDto);
    }

    @Operation(summary  = "Atualizada um produto", description  = "Envie para esse endpoint o json contendo todo objeto e o id,mude apenas os dados que queira atualizar.")
    @PutMapping("/produto")
    public ResponseEntity<Produto> edit(@RequestBody Produto produto){
        
        return ResponseEntity.ok().body(produtoService.edit(produto));
        
    }

    @DeleteMapping("/produto/{id}")
    public void deleteById(@PathVariable("id") Long id){
        produtoService.deleteById(id);
    }
    
}
