package com.andretavares.testesecurity.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    // @OneToOne(mappedBy = "cor")
    // @JsonIgnore
    // private Produto produto;

    @JsonIgnore
    @ManyToOne
    private Categoria categoria;

    public Cor(String nome, Categoria categoria) {
        this.nome = nome;
        this.categoria = categoria;
    }

    // Getters e setters

    
}
