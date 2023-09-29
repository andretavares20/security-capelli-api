package com.andretavares.testesecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andretavares.testesecurity.entities.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {
    
}
