package com.andretavares.testesecurity.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andretavares.testesecurity.entities.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria,Long>{

    Categoria findByNome(String nome);
    
}
