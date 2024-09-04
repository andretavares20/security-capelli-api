package com.capellimegahair.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capellimegahair.api.entities.Carrinho;

public interface CarrinhoRepository extends JpaRepository<Carrinho,Long>{

    Optional<Carrinho> findByUserIdAndProdutoId(Long username, Long produtoId);

    List<Carrinho> findByUserId(Long username);

    List<Carrinho> findAllByUserId(Long username);
    
}
