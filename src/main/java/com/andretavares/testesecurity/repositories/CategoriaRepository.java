package com.andretavares.testesecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andretavares.testesecurity.entities.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria,Long>{

    
    
}
