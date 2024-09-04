package com.capellimegahair.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capellimegahair.api.entities.Estoque;

public interface EstoqueRepository extends JpaRepository<Estoque,Long> {
    
}
