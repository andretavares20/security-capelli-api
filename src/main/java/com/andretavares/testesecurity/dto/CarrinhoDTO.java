package com.andretavares.testesecurity.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.andretavares.testesecurity.entities.Produto;
import com.andretavares.testesecurity.entities.Tamanho;
import com.andretavares.testesecurity.entities.Tecnica;
import com.andretavares.testesecurity.entities.User;
import com.andretavares.testesecurity.entities.Volume;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class CarrinhoDTO {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn
    @ManyToOne
    private Produto produto;//tem que ver legal esse relacionamento//se tiver identificador unico
    @JoinColumn
    @ManyToOne
    private User user;
    private Long quantidade;
    private BigDecimal preco;
    private BigDecimal quantia;
    private LocalDateTime dataCriacao;

    @ManyToOne
    @JoinColumn(name = "tecnica_id")
    private Tecnica tecnica;

    @ManyToOne
    @JoinColumn(name = "tamanho_id")
    private Tamanho tamanho;

    @ManyToOne
    @JoinColumn(name = "volume_id")
    private Volume volume;

}
