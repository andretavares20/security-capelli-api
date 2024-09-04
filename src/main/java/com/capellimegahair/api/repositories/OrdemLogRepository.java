package com.capellimegahair.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capellimegahair.api.entities.OrdemLog;

public interface OrdemLogRepository extends JpaRepository<OrdemLog,Long>{
    
    void deleteByOrdemId(Long ordemItem);

}
