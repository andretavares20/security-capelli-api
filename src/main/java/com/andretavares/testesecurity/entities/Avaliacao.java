package com.andretavares.testesecurity.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Avaliacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    @JsonIgnoreProperties("avaliacoes") // Indica que a propriedade "avaliacoes" em Produto deve ser ignorada durante a serialização
    @JsonIgnore
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnoreProperties("avaliacoes") // Indica que a propriedade "avaliacoes" em Usuario deve ser ignorada durante a serialização
    private User user;

    private int rating;
    private String titulo;
    private String descricao;

}
