package com.capellimegahair.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capellimegahair.api.entities.Arquivo;
import com.capellimegahair.api.entities.Carrinho;

public interface ArquivoRepository extends JpaRepository<Arquivo,Long>{
    
    List<Arquivo> findAllByProdutoId(Long produtoId);

}
