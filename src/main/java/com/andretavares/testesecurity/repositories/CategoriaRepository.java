package com.andretavares.testesecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andretavares.testesecurity.entities.CategoriaModel;

public interface CategoriaRepository extends JpaRepository<CategoriaModel,Long> {
    
}
