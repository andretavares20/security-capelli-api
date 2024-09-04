package com.capellimegahair.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capellimegahair.api.entities.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {
    
}
