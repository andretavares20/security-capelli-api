package com.capellimegahair.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capellimegahair.api.entities.Arquivo;
import com.capellimegahair.api.entities.Tamanho;

public interface TamanhoRepository extends JpaRepository<Tamanho,Long>{
    Tamanho findByCm(String cm);
}
