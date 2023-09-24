package com.andretavares.testesecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andretavares.testesecurity.entities.Estoque;

public interface EstoqueRepository extends JpaRepository<Estoque,Long> {
    
}
