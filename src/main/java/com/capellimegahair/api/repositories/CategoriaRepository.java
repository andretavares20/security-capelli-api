package com.capellimegahair.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capellimegahair.api.entities.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria,Long>{

    Categoria findByNome(String nome);

    List<Categoria> findAllBySituacao(Boolean stiuacao);
    
}
