package com.andretavares.testesecurity.entities;

import com.andretavares.testesecurity.entities.keys.ProdutoTamanhoKey;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "produto_tamanho")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoTamanho {
    
    @EmbeddedId
    ProdutoTamanhoKey id;

    @ManyToOne
    @MapsId("produtoId")
    @JoinColumn(name = "produto_id")
    Produto produto;

    @ManyToOne
    @MapsId("tamanhoId")
    @JoinColumn(name = "tamanho_id")
    Tamanho tamanho;

    private Boolean disponivel;

}
