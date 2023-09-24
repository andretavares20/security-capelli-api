package com.andretavares.testesecurity.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andretavares.testesecurity.entities.Produto;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {
    
    @PostMapping("/adicionar-produto")
    public Produto postProdutoCarrinho(@RequestBody Produto produto,@RequestParam("userId") Long userId){

        return null;

    }

    @DeleteMapping("/remover-produo")
    public Produto deleteProdutoCarrinho(@RequestParam("produtoId") Long produtoId,
        @RequestParam("userId") Long userId){

            return null;

    }

    @GetMapping("/calcular-frete")
    public Double calculaFrete(@RequestParam("userId") Long userId,@RequestParam("cep") String cep){

        return null;

    }

    @PostMapping("/alterar-quantidade")
    public ResponseEntity<?> alteraQuantidadeProdutoCarrinho(@RequestParam("userId") Long userId,
        @RequestParam("produtoId") Long produtoId,@RequestParam("quantidade") Long id ){

        return null;

    }

    @PostMapping("/finalizar-compra")
    public ResponseEntity<?> finalizaCompra(@RequestParam("userId") Long userId){

        return null;

    }


}
