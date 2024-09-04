package com.capellimegahair.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capellimegahair.api.entities.ProdutoVolume;

public interface ProdutoVolumeRepository extends JpaRepository<ProdutoVolume, Long> {
}
