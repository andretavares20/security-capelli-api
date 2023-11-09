package com.andretavares.testesecurity.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.andretavares.testesecurity.entities.Cor;

@Repository
public interface CorRepository extends JpaRepository<Cor, Long> {

    List<Cor> findAllByCategoriaId(Long corId);

}
