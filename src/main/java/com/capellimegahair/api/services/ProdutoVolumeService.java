package com.capellimegahair.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capellimegahair.api.entities.ProdutoVolume;
import com.capellimegahair.api.repositories.ProdutoVolumeRepository;

@Service
public class ProdutoVolumeService {

    @Autowired
    private ProdutoVolumeRepository produtoVolumeRepository;

    public List<ProdutoVolume> findAll() {
        return produtoVolumeRepository.findAll();
    }

    public ProdutoVolume findById(Long id) {
        return produtoVolumeRepository.findById(id).orElse(null);
    }

    public ProdutoVolume save(ProdutoVolume produtoVolume) {
        return produtoVolumeRepository.save(produtoVolume);
    }

    public void deleteById(Long id) {
        produtoVolumeRepository.deleteById(id);
    }
}
