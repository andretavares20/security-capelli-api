package com.andretavares.testesecurity.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andretavares.testesecurity.entities.OrdemItem;

public interface OrdemItemRepository extends JpaRepository<OrdemItem,Long>{
    
    List<OrdemItem> findAllByProdutoId(Long produtoId);

}
