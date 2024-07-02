package com.andretavares.testesecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andretavares.testesecurity.entities.ProdutoVolume;

public interface ProdutoVolumeRepository extends JpaRepository<ProdutoVolume, Long> {
}
