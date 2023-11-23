package com.andretavares.testesecurity.entities;

import com.andretavares.testesecurity.entities.keys.ProdutoVolumeKey;

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
@Table(name = "produto_volume")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoVolume {

    @EmbeddedId
    ProdutoVolumeKey id;

    @ManyToOne
    @MapsId("produtoId")
    @JoinColumn(name = "produto_id")
    Produto produto;

    @ManyToOne
    @MapsId("volumeId")
    @JoinColumn(name = "volume_id")
    Volume volume;

    private Boolean disponivel;

}
