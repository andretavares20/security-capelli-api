package com.andretavares.testesecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andretavares.testesecurity.entities.Ordem;

public interface OrdemRepository extends JpaRepository<Ordem,Long>{
    
}
