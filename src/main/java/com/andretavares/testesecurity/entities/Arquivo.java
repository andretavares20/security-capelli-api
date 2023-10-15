package com.andretavares.testesecurity.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Arquivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String caminho;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    public Arquivo(String nome, String caminho, Produto produto) {
        this.nome = nome;
        this.caminho = caminho;
        this.produto = produto;
    }
    
    // Getters e Setters

    
}
