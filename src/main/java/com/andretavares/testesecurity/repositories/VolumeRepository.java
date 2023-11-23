package com.andretavares.testesecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andretavares.testesecurity.entities.Volume;



public interface VolumeRepository extends JpaRepository<Volume,Long>{
    
}
