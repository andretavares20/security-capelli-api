package com.andretavares.testesecurity.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    // @JsonIgnore
    // @ManyToMany(mappedBy = "volumes")
    // private Set<Produto> produtos = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "volume")
    private List<Carrinho> carrinhos;

    @JsonIgnore
    @OneToMany(mappedBy = "volume")
    private List<OrdemItem> ordemItems;

    public Volume(String gramas) {
        this.gramas = gramas;
    }

    

}
