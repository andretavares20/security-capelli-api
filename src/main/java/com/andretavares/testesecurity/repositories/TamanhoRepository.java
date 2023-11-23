package com.andretavares.testesecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andretavares.testesecurity.entities.Arquivo;
import com.andretavares.testesecurity.entities.Tamanho;

public interface TamanhoRepository extends JpaRepository<Tamanho,Long>{
    
}
