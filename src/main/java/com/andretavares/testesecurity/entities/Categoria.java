package com.andretavares.testesecurity.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String urlImagem;
    @Column(length = 500)
    private String descricao;
    @Column(columnDefinition = "TINYINT(1)")
    private Boolean situacao;


    @OneToMany(mappedBy = "categoria")
    @JsonIgnore
    private List<Produto> produtos;

    @OneToMany(mappedBy = "categoria")
    private List<Arquivo> arquivos;

    public Categoria(String nome) {
        this.nome = nome;
    }

    public Categoria(String nome, String urlImagem, String descricao, Boolean situacao) {
        this.nome = nome;
        this.urlImagem = urlImagem;
        this.descricao = descricao;
        this.situacao = situacao;
    }

    

    // Getters e setters

    
}
