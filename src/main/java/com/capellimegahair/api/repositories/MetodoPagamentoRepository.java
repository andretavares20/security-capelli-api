package com.capellimegahair.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capellimegahair.api.entities.MetodoPagamento;

public interface MetodoPagamentoRepository extends JpaRepository<MetodoPagamento,Long>{
    
}
