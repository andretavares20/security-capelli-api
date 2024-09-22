package com.capellimegahair.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capellimegahair.api.dto.TamanhoDto;
import com.capellimegahair.api.entities.ProdutoTamanho;
import com.capellimegahair.api.services.TamanhoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tamanho")
@RequiredArgsConstructor
public class TamanhoController {

    @Autowired
    private TamanhoService tamanhoService;

    @GetMapping()
    public ResponseEntity<List<TamanhoDto>> findAllTamanhos() {
        List<TamanhoDto> tamanhos = tamanhoService.findAll();
        return ResponseEntity.ok(tamanhos);
    }

    @GetMapping("/produto-tamanho")
    public ResponseEntity<List<ProdutoTamanho>> findAllProdutoTamanhoByProdutoId(@RequestParam("produtoId") Long produtoID) {
        List<ProdutoTamanho> listProdutoTamanho = tamanhoService.findAllProdutoTamanhoByProdutoId(produtoID);
        return ResponseEntity.ok().body(listProdutoTamanho);
    }
    
}
