package com.andretavares.testesecurity.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Volume implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gramas;

    @ManyToMany(mappedBy = "volumes")
    private Set<Produto> produtos = new HashSet<>();

    @OneToMany(mappedBy = "volume")
    private List<Carrinho> carrinhos;

    @OneToMany(mappedBy = "volume")
    private List<OrdemItem> ordemItems;

}
