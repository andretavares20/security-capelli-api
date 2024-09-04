package com.capellimegahair.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capellimegahair.api.entities.Cor;

@Repository
public interface CorRepository extends JpaRepository<Cor, Long> {

    List<Cor> findAllByCategoriaId(Long corId);

}
