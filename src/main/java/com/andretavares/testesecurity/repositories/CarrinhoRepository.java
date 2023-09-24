package com.andretavares.testesecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andretavares.testesecurity.entities.CarrinhoModel;

public interface CarrinhoRepository extends JpaRepository<CarrinhoModel,Long> {
    
}
