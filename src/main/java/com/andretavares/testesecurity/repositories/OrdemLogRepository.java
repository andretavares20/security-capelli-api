package com.andretavares.testesecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andretavares.testesecurity.entities.OrdemLog;

public interface OrdemLogRepository extends JpaRepository<OrdemLog,Long>{
    
}
