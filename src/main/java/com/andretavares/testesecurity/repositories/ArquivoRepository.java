package com.andretavares.testesecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andretavares.testesecurity.entities.Arquivo;

public interface ArquivoRepository extends JpaRepository<Arquivo,Long>{
    
}
