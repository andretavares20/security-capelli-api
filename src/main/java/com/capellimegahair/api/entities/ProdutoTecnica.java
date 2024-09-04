package com.capellimegahair.api.entities;



import com.capellimegahair.api.entities.keys.ProdutoTecnicaKey;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "produto_tecnica")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoTecnica {
    
    @EmbeddedId
    ProdutoTecnicaKey id;

    @ManyToOne
    @MapsId("produtoId")
    @JoinColumn(name = "produto_id")
    Produto produto;

    @ManyToOne
    @MapsId("tecnicaId")
    @JoinColumn(name = "tecnica_id")
    Tecnica tecnica;

    private Boolean disponivel;

}
