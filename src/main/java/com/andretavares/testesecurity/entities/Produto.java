package com.andretavares.testesecurity.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Double preco;

    private String tamanho;

    private String volume;

    private String tecnica;

    private String descricao;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaModel categoria;

}
