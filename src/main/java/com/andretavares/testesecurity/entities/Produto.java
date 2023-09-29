package com.andretavares.testesecurity.entities;

import java.io.Serializable;
import java.math.BigDecimal;

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
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String picture;

    @JoinColumn
    @ManyToOne
    private Categoria categoria;
    private BigDecimal price;
    private Long estoque;
    
    public Produto(String name, String description, String picture, Categoria categoria, BigDecimal price,
            Long estoque) {
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.categoria = categoria;
        this.price = price;
        this.estoque = estoque;
    }

    
}
