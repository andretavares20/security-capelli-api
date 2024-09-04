package com.capellimegahair.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capellimegahair.api.entities.Produto;

public interface ProdutoRepository extends JpaRepository<Produto,Long>{
    
    List<Produto> findAllByCategoriaId(Long categoriaId);

}
