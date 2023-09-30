package com.andretavares.testesecurity.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class Carrinho implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn
    @ManyToOne
    private Produto produto;//tem que ver legal esse relacionamento//se tiver identificador unico
    @JoinColumn
    @ManyToOne
    private User user;
    private Double quantidade;
    private BigDecimal preco;
    private BigDecimal quantia;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataCriacao;
}
