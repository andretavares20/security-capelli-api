package com.andretavares.testesecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andretavares.testesecurity.entities.PagamentoModel;

public interface PagamentoRepository extends JpaRepository<PagamentoModel,Long>{
    
}
