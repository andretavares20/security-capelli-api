package com.andretavares.testesecurity.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
    private BigDecimal price;
    private Long estoque;
    
    @ManyToMany
    @JoinTable(
        name = "produto_tamanho",
        joinColumns = @JoinColumn(name = "produto_id"),
        inverseJoinColumns = @JoinColumn(name = "tamanho_id")
    )
    private Set<Tamanho> tamanhos = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "produto_volume",
        joinColumns = @JoinColumn(name = "produto_id"),
        inverseJoinColumns = @JoinColumn(name = "volume_id")
    )
    private Set<Volume> volumes = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "produto_tecnica",
        joinColumns = @JoinColumn(name = "produto_id"),
        inverseJoinColumns = @JoinColumn(name = "tecnica_id")
    )
    private Set<Tecnica> tecnicas = new HashSet<>();

    @OneToOne()
    @JoinColumn(name = "cor_id", referencedColumnName = "id")
    private Cor cor;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    private List<Arquivo> arquivos = new ArrayList<>();

    public Produto(String name, String description, Categoria categoria, Cor cor, BigDecimal price,
            Long estoque) {
        this.name = name;
        this.description = description;
        this.cor = cor;
        this.price = price;
        this.estoque = estoque;
    }
    
    // Getters e setters

    
}
