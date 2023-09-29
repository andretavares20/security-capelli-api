package com.andretavares.testesecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andretavares.testesecurity.entities.MetodoPagamento;

public interface MetodoPagamentoRepository extends JpaRepository<MetodoPagamento,Long>{
    
}
