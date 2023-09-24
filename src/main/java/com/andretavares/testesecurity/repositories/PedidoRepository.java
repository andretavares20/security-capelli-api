package com.andretavares.testesecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andretavares.testesecurity.entities.PedidoModel;

public interface PedidoRepository extends JpaRepository<PedidoModel,Long> {
    
}
