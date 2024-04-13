package com.andretavares.testesecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.andretavares.testesecurity.entities.Avaliacao;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    // Se precisar de consultas específicas, podem ser adicionadas aqui
}
