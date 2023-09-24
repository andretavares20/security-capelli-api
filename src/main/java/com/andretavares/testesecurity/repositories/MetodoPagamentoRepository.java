package com.andretavares.testesecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andretavares.testesecurity.entities.MetodoPagamentoModel;

public interface MetodoPagamentoRepository extends JpaRepository<MetodoPagamentoModel,Long>{
    
}
