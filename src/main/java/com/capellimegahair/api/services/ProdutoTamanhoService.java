package com.capellimegahair.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capellimegahair.api.entities.ProdutoTamanho;
import com.capellimegahair.api.repositories.ProdutoTamanhoRepository;

@Service
public class ProdutoTamanhoService {

    @Autowired
    private ProdutoTamanhoRepository produtoTamanhoRepository;

    public List<ProdutoTamanho> findAll() {
        return produtoTamanhoRepository.findAll();
    }

    public ProdutoTamanho findById(Long id) {
        return produtoTamanhoRepository.findById(id).orElse(null);
    }

    public ProdutoTamanho save(ProdutoTamanho produtoTamanho) {
        return produtoTamanhoRepository.save(produtoTamanho);
    }

    public void deleteById(Long id) {
        produtoTamanhoRepository.deleteById(id);
    }
}