package com.andretavares.testesecurity.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Pagamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double valor;

    private LocalDate dataPagamento;

    
    private Carrinho carrinho;

    @ManyToOne
    @JoinColumn(name = "metodo_pagamento_id")
    private MetodoPagamento metodoPagamento;

}
