package com.andretavares.testesecurity.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andretavares.testesecurity.entities.ProdutoTamanho;
import com.andretavares.testesecurity.repositories.ProdutoTamanhoRepository;

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