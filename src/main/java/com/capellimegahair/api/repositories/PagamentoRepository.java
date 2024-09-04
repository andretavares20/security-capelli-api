package com.capellimegahair.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capellimegahair.api.entities.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento,Long>{
    
}
