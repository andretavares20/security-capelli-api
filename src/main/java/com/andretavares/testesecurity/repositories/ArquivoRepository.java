package com.andretavares.testesecurity.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andretavares.testesecurity.entities.Arquivo;
import com.andretavares.testesecurity.entities.Carrinho;

public interface ArquivoRepository extends JpaRepository<Arquivo,Long>{
    
    List<Arquivo> findAllByProdutoId(Long produtoId);

}
