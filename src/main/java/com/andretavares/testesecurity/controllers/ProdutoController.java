package com.andretavares.testesecurity.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.andretavares.testesecurity.entities.Produto;
import com.andretavares.testesecurity.repositories.CorRepository;
import com.andretavares.testesecurity.services.ProdutoService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/client/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    

    @Operation(summary = "Retorna todos os produto existentes no sistema.")
    @GetMapping()
    public List<Produto> findAll() {

        return produtoService.findAll();
    }

    @Operation(summary = "Retorna um produto pelo id", description = "Envie para esse endpoint o id do produto")
    @GetMapping("/{id}")
    public Produto findById(@PathVariable("id") Long id) {
        return produtoService.findById(id);
    }

    @GetMapping("lista-produtos-por-categoriaId/{categoriaId}")
    public ResponseEntity<List<Produto>> listaProdutosPorCategoriaId(@PathVariable("categoriaId") Long categoriaId){

        return ResponseEntity.ok().body(produtoService.listaProdutosPorCategoriaId(categoriaId));

    }

    @GetMapping("lista-produtos-por-nomeCategoria/{nomeCategoria}")
    public ResponseEntity<List<Produto>> listaProdutosPorCategoriaNome(@PathVariable("nomeCategoria") String nomeCategoria){

        return ResponseEntity.ok().body(produtoService.listaProdutosPorCategoriaNome(nomeCategoria));

    }

    @GetMapping("lista-imagens-produto/{produtoId}")
    public ResponseEntity<List<MultipartFile>> listaImagensProduto(@PathVariable("produtoId") Long produtoId) throws IOException{

        return ResponseEntity.ok().body(produtoService.listaImagensProduto(produtoId));

    }



}
