package com.andretavares.testesecurity.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andretavares.testesecurity.dto.ProdutoQuantidadeDto;
import com.andretavares.testesecurity.entities.Estoque;
import com.andretavares.testesecurity.entities.Produto;
import com.andretavares.testesecurity.services.ProdutoService;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;
    
    @PostMapping
    public ResponseEntity<Estoque> postProduto(@RequestBody Produto produto){
        
        Estoque estoqueResponse = produtoService.postProduto(produto);

        return ResponseEntity.ok().body(estoqueResponse);

    }

    @PostMapping("/list")
    public ResponseEntity<List<Estoque>> postListProduto(@RequestBody List<ProdutoQuantidadeDto> listProdutoQuantidadeDto){
        
        List<Estoque> listProdutoQuantidadeDtoReponse = produtoService.postListProduto(listProdutoQuantidadeDto);

        return ResponseEntity.ok().body(listProdutoQuantidadeDtoReponse);

    }

    @GetMapping()
    public Produto getProduto(@RequestParam("id") Long id){
        return null;
    }

    @GetMapping("/list")
    public List<Produto> getListProduto(){
        return null;
    }

    @GetMapping("/list/nome")
    public List<Produto> getListNomeProduto(@RequestParam("nome") String nome){
        return null;
    }

    @GetMapping("/list/filtro")
    public List<Produto> getListFiltroProduto(@RequestParam("tamanho") String tamanho,
        @RequestParam("volume") String volume,@RequestParam("tecnica") String tecnica){
        
        return null;

    }

    @PutMapping
    public Produto putProduto(@RequestParam("id") Long id){
        return null;
    }

    @PutMapping("/list/filtro")
    public Produto putListProduto(@RequestParam("tamanho") String tamanho,
        @RequestParam("volume") String volume,@RequestParam("tecnica") String tecnica){

        return null;

    }

    @DeleteMapping
    public Produto deleteProduto(@RequestParam("id") Long id){
        return null;
    }

    @DeleteMapping("/list/filtro")
    public Produto deleteListProduto(@RequestParam("tamanho") String tamanho,
        @RequestParam("volume") String volume,@RequestParam("tecnica") String tecnica){

        return null;

    }

}
