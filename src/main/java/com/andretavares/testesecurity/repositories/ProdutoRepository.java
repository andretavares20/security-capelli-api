package com.andretavares.testesecurity.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andretavares.testesecurity.entities.Produto;

public interface ProdutoRepository extends JpaRepository<Produto,Long>{
    
    List<Produto> findAllByCategoriaId(Long categoriaId);

}
