package com.capellimegahair.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Arquivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String caminho;
    private String nomeVerdadeiro;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    @JsonIgnore
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @JsonIgnore
    private Categoria categoria;

    public Arquivo(String nome, String caminho, Produto produto) {
        this.nome = nome;
        this.caminho = caminho;
        this.produto = produto;
    }

    public Arquivo(String nome, String caminho, String nomeVerdadeiro, Produto produto) {
        this.nome = nome;
        this.caminho = caminho;
        this.nomeVerdadeiro = nomeVerdadeiro;
        this.produto = produto;
    }
    
    public Arquivo(String nome, String caminho, String nomeVerdadeiro, Categoria categoria) {
        this.nome = nome;
        this.caminho = caminho;
        this.nomeVerdadeiro = nomeVerdadeiro;
        this.categoria = categoria;
    }

    
}
