package com.capellimegahair.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capellimegahair.api.entities.ProdutoTamanho;

public interface ProdutoTamanhoRepository extends JpaRepository<ProdutoTamanho, Long> {
    List<ProdutoTamanho> findAllByProdutoId(Long produtoId);
}
