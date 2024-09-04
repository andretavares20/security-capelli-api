package com.capellimegahair.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capellimegahair.api.entities.OrdemItem;

public interface OrdemItemRepository extends JpaRepository<OrdemItem,Long>{
    
    List<OrdemItem> findAllByProdutoId(Long produtoId);

}
