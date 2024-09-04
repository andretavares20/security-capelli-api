package com.capellimegahair.api.entities;

import java.io.Serializable;
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
public class Tecnica implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean situacao;

    // @JsonIgnore
    // @ManyToMany(mappedBy = "tecnicas")
    // private Set<Produto> produtos = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "tecnica")
    private List<Carrinho> carrinhos;

    @JsonIgnore
    @OneToMany(mappedBy = "tecnica")
    private List<OrdemItem> ordemItems;

    public Tecnica(String nome) {
        this.nome = nome;
    }

    

}
