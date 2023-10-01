package com.andretavares.testesecurity.entities;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String picture;

    // @OneToOne(mappedBy = "cor")
    // private Cor cor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cor_id", referencedColumnName = "id")
    private Cor cor;

    private Long price;
    private Long estoque;
    public Produto(String name, String description, String picture, Categoria categoria, Cor cor, Long price,
            Long estoque) {
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.cor = cor;
        this.price = price;
        this.estoque = estoque;
    }
    
    // Getters e setters

    
}
