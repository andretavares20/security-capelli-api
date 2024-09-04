package com.capellimegahair.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capellimegahair.api.entities.Volume;



public interface VolumeRepository extends JpaRepository<Volume,Long>{
    
}
