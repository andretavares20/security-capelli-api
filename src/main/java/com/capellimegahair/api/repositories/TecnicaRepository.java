package com.capellimegahair.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capellimegahair.api.entities.Tecnica;

public interface TecnicaRepository extends JpaRepository<Tecnica,Long>{
    Tecnica findByNome(String nomeTenica);
}
