package com.andretavares.testesecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andretavares.testesecurity.entities.Carrinho;

public interface CarrinhoRepository extends JpaRepository<Carrinho,Long>{
    
}
