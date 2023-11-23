package com.andretavares.testesecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andretavares.testesecurity.entities.Tecnica;

public interface TecnicaRepository extends JpaRepository<Tecnica,Long>{
    
}
