package com.andretavares.testesecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andretavares.testesecurity.entities.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento,Long>{
    
}
